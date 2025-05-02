package com.liga.service;

import com.liga.converter.KitchenDishMapper;
import com.liga.converter.KitchenOrderDtoToResponseMapper;
import com.liga.converter.KitchenOrderDtoToWaiterOrderSendDtoMapper;
import com.liga.converter.KitchenOrderMapper;
import com.liga.dto.DishDto;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderResponse;
import com.liga.entities.CompositeOrderToDishId;
import com.liga.entities.KitchenOrderEntity;
import com.liga.entities.OrderToDishEntity;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.feign.WaiterFeignClient;
import com.liga.objects.KitchenStatus;
import com.liga.repository.KitchenDishRepository;
import com.liga.repository.KitchenOrderRepository;
import com.liga.repository.KitchenOrderToDishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {
    private final KitchenOrderMapper kitchenOrderMapper;
    private final KitchenDishRepository kitchenDishRepository;
    private final KitchenDishMapper kitchenDishMapper;
    private final KitchenOrderToDishRepository kitchenOrderToDishRepository;
    private final KitchenOrderDtoToWaiterOrderSendDtoMapper kitchenOrderDtoToWaiterOrderSendDtoMapper;
    private final WaiterFeignClient waiterFeignClient;
    private final KitchenOrderRepository kitchenOrderRepository;
    private final KitchenOrderDtoToResponseMapper kitchenOrderDtoToResponseMapper;
    private final DishService dishService;

    @Override
    public Set<KitchenOrderResponse> getAllOrders() {
        log.debug("trying to get all orders");
        List<KitchenOrderEntity> setEntities = kitchenOrderRepository.findAllDistinct();

        var result = setEntities.stream()
                .map(kitchenOrderMapper::toDto)
                .map(kitchenOrderDtoToResponseMapper::mapDtoToResponse)
                .collect(Collectors.toSet());
        log.debug("getAllOrders returned {} orders", result.size());
        return result;
    }

    /**
     * Создаёт новый заказ на кухне, устанавливает ему статус
     * и сохраняет в базе данных.
     */
    @Override
    public void createOrder(KitchenOrderDto order) {

        log.debug("trying to create order {}", order);

        order.setStatus(KitchenStatus.CREATED);

        kitchenOrderRepository.save(kitchenOrderMapper.toEntity(order));


        log.debug("Order saved {}", order);
        if (dishesIsAvailable(order)) {
            System.out.println("Dishes available"+order);
            log.debug("dishes available {}", order.getOrderDishes());
            createOrderToDish(order);
            log.debug("created order-to-dish relationship for waiterOrderId: {}", order.getOrderIdWaiterService());
            System.out.println("ok");
            acceptOrder(order.getOrderIdWaiterService());
        } else {
            rejectOrder(order.getOrderIdWaiterService());
        }
    }

    @Override
    public void acceptOrder(Long orderId) {
        log.debug("trying to accept order with id: {}", orderId);
        if (!kitchenOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenOrderRepository.updateStatusById(orderId, KitchenStatus.ACCEPTED);
        log.debug("order with id {} mark as ACCEPTED", orderId);
    }

    /**
     * Отменяет заказ по указанному идентификатору и отправляет обновленную
     * информацию о заказе в сервис официантов.
     */
    @Override
    public void rejectOrder(Long orderId) {
        log.debug("trying to reject order with id: {}", orderId);
        if (!kitchenOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenOrderRepository.updateStatusById(orderId, KitchenStatus.REJECTED);
        log.debug("order with id {} mark as REJECTED", orderId);

        log.debug("Sending rejected order with id={} to waiter service", orderId);
        waiterFeignClient.sendCanceledOrderToWaiter(kitchenOrderDtoToWaiterOrderSendDtoMapper
                .map(getOrderById(orderId)));
        log.debug("Rejected order with id={} sent to waiter service", orderId);
    }

    @Override
    public void setStatusReady(Long orderId) {
        if (!kitchenOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenOrderRepository.updateStatusById(orderId, KitchenStatus.COOKED);
    }

    @Override
    public KitchenOrderDto getOrderById(Long orderId) {
        log.debug("trying to get order by id: {}", orderId);
        var result = kitchenOrderMapper.toDto(kitchenOrderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException(String.format("Order with id %s not found", orderId))));
        log.debug("successfully retrieved order with id: {}", orderId);
        return result;
    }



    /**
     * Проверяет, доступны ли все блюда из заказа в текущем списке блюд на кухне.
     * <p>
     * Метод получает уникальные короткие имена всех блюд, доступных на кухне,
     * и сравнивает их с блюдами, указанными в заказе. Если все блюда из заказа
     * присутствуют среди доступных блюд в нужном количестве, возвращает true, иначе — false.
     */
    @Override
    public Boolean dishesIsAvailable(KitchenOrderDto order) {
        log.debug("trying to check available of dishes");

        Map<String, DishDto> dishDtoSet = kitchenDishRepository.findAllDistinct()
                .stream()
                .map(kitchenDishMapper::toDto)
                .collect(Collectors.toMap(DishDto::shortName, dishDto -> dishDto));

                var result = order.getOrderDishes()
                        .stream()
                .allMatch(orderedDish ->
                Optional.ofNullable(dishDtoSet.get(orderedDish.shortName()))
                        .map(dishDto -> dishDto.balance() >= orderedDish.dishesNumber())
                        .orElse(false)
        );
        log.debug("dishes: {} available: {}", dishDtoSet, result);
        return result;
    }

    /**
     * Создает и сохраняет связи между заказом и блюдом в таблице
     * OrderToDish
     * Для каждого блюда из списка будет создана отдельная запись связи с заказом.
     **/
    @Override
    public void createOrderToDish(KitchenOrderDto order) {
        log.debug("trying to create orderToDish {}", order);

        for (DishDto dishDto : order.getOrderDishes()) {

            OrderToDishEntity orderToDishEntity = OrderToDishEntity.builder()
                    .id(new CompositeOrderToDishId(order.getOrderIdWaiterService(),
                            dishService.getDishByShortName(dishDto.shortName()).id()))
                    .dishEntity(kitchenDishMapper.toEntity(dishService
                            .getDishById(dishService
                                    .getDishByShortName(dishDto.shortName()).id())))
                    .order(kitchenOrderMapper.toEntity(getOrderById(order.getOrderIdWaiterService())))
                    .dishesNumber(dishDto.dishesNumber())
                    .build();

            kitchenOrderToDishRepository.save(orderToDishEntity);
            log.debug("successfully created orderToDish with id: {}", orderToDishEntity.getId());
        }
    }

}
