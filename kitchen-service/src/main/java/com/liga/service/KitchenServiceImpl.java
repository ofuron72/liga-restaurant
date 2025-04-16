package com.liga.service;

import com.liga.converter.KitchenDishMapper;
import com.liga.converter.KitchenOrderDtoToResponseMapper;
import com.liga.converter.KitchenOrderDtoToWaiterOrderSendDtoMapper;
import com.liga.converter.KitchenOrderMapper;
import com.liga.dto.DishDto;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderResponse;
import com.liga.entities.CompositeOrderToDishId;
import com.liga.entities.KitchenOrder;
import com.liga.entities.OrderToDish;
import com.liga.exceptions.DishNotFoundException;
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

    @Override
    public Set<KitchenOrderResponse> getAllOrders() {
        List<KitchenOrder> setEntities = kitchenOrderRepository.findAllDistinct();

        return setEntities.stream()
                .map(kitchenOrderMapper::toDto)
                .map(kitchenOrderDtoToResponseMapper::mapDtoToResponse)
                .collect(Collectors.toSet());
    }

    /**
     * Создаёт новый заказ на кухне, устанавливает ему статус {@code CREATED} и сохраняет в базе данных.
     * <p>
     * После сохранения заказа метод проверяет, доступны ли все блюда из заказа на кухне.
     * Если все блюда доступны, создаёт связи между заказом и блюдами, а затем переводит заказ в статус "принят".
     * Если хотя бы одного блюда нет в наличии, заказ переводится в статус "отклонён".
     * </p>
     *
     * @param order объект {@link KitchenOrderDto}, содержащий информацию о заказе и списке блюд.
     */
    @Override
    public void createOrder(KitchenOrderDto order) {

        order.setStatus(KitchenStatus.CREATED);

        kitchenOrderRepository.save(kitchenOrderMapper.toEntity(order));

        log.info("Order saved with status: {}", KitchenStatus.CREATED);

        if (dishesIsAvailable(order)) {
            log.info("All dishes in order with id {} are available", order.getOrderIdWaiterService());

            createOrderToDish(order);
            log.info("created order-to-dish relationship for waiterOrderId: {}", order.getOrderIdWaiterService());

            acceptOrder(order.getOrderIdWaiterService());
        } else {
            rejectOrder(order.getOrderIdWaiterService());
        }
    }

    @Override
    public void acceptOrder(Long orderId) {
        if (!kitchenOrderRepository.existsById(orderId)) {
            log.warn("Order with id {} does not exist. Cannot accept", orderId);
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenOrderRepository.updateStatusById(orderId, KitchenStatus.ACCEPTED);
        log.info("order with id {} mark as ACCEPTED", orderId);
    }

    @Override
    public void rejectOrder(Long orderId) {
        if (!kitchenOrderRepository.existsById(orderId)) {
            log.warn("Order with id {} does not exist. Cannot reject", orderId);
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }

        kitchenOrderRepository.updateStatusById(orderId, KitchenStatus.REJECTED);
        log.info("order with id {} mark as REJECTED", orderId);

        log.debug("Sending rejected order with id={} to waiter service", orderId);
        waiterFeignClient.sendCanceledOrderToWaiter(kitchenOrderDtoToWaiterOrderSendDtoMapper
                .map(getOrderById(orderId)));
        log.info("Rejected order with id={} sent to waiter service", orderId);
    }

    @Override
    public void setStatusReady(Long orderId) {
        if (!kitchenOrderRepository.existsById(orderId)) {
            log.warn("Order with id={} not found. Cannot set status to COOKED.", orderId);
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenOrderRepository.updateStatusById(orderId, KitchenStatus.COOKED);
        log.info("Order with id={} marked as COOKED", orderId);
    }

    @Override
    public KitchenOrderDto getOrderById(Long orderId) {
        return kitchenOrderMapper.toDto(kitchenOrderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException(String.format("Order with id %s not found", orderId))));
    }

    public DishDto getDishByShortName(String shortName) {
        return kitchenDishMapper.toDto(kitchenDishRepository.findByShortName(shortName)
                .orElseThrow(() -> new DishNotFoundException(String.format("Dish with shortName %s not found", shortName))));
    }

    @Override
    public DishDto getDishById(Long id) {
        return kitchenDishMapper.toDto(kitchenDishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(String.format("Dish with id %s not found", id))));
    }

    /**
     * Проверяет, доступны ли все блюда из заказа в текущем списке блюд на кухне.
     * <p>
     * Метод получает уникальные короткие имена всех блюд, доступных на кухне,
     * и сравнивает их с блюдами, указанными в заказе. Если все блюда из заказа
     * присутствуют среди доступных блюд, возвращает {@code true}, иначе — {@code false}.
     * </p>
     *
     * @param order объект {@link KitchenOrderDto}, содержащий список заказанных блюд.
     * @return {@code true}, если все блюда из заказа доступны на кухне;
     * {@code false} в противном случае.
     */
    @Override
    public Boolean dishesIsAvailable(KitchenOrderDto order) {

        Set<String> dishDtoSet = kitchenDishRepository.findAllDistinct()
                .stream()
                .map(kitchenDishMapper::toDto)
                .map(DishDto::shortName)
                .collect(Collectors.toSet());

        return dishDtoSet.containsAll(order.getOrderDishes()
                .stream()
                .map(DishDto::shortName)
                .collect(Collectors.toSet()));
    }

    /**
     * Создает и сохраняет связи между заказом и блюдом в таблице
     * OrderToDish
     * @param order объект {@link KitchenOrderDto}, содержащий идентификатор заказа и список блюд.
     *  * Для каждого блюда из списка будет создана отдельная запись связи с заказом.
     * */
    @Override
    public void createOrderToDish(KitchenOrderDto order) {

        for (DishDto dishDto : order.getOrderDishes()) {

            OrderToDish orderToDish = OrderToDish.builder()
                    .id(new CompositeOrderToDishId(order.getOrderIdWaiterService(),
                            getDishByShortName(dishDto.shortName()).id()))
                    .dish(kitchenDishMapper.toEntity(getDishById(getDishByShortName(dishDto.shortName()).id())))
                    .order(kitchenOrderMapper.toEntity(getOrderById(order.getOrderIdWaiterService())))
                    .dishesNumber(dishDto.dishesNumber())
                    .build();

            kitchenOrderToDishRepository.save(orderToDish);
        }
    }

}
