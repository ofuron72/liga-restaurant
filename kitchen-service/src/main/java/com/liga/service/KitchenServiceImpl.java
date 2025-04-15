package com.liga.service;
import com.liga.converter.KitchenOrderMapper;
import com.liga.dto.KitchenOrderDto;
import com.liga.entities.KitchenOrder;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.objects.KitchenStatus;
import com.liga.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Set<KitchenOrderDto> getAllOrders() {
        List<KitchenOrder> setEntities = kitchenOrderRepository.findAllDistinct();

        return setEntities.stream()
                .map(kitchenOrderMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void createOrder(KitchenOrderDto order) {
        order.setStatus(KitchenStatus.CREATED);
        System.out.println("KithcenService: createOrder before save" + order);
        kitchenOrderRepository.save(kitchenOrderMapper.toEntity(order));
        System.out.println("KithcenService: createOrder after save" + order);

        if (dishesIsAvailable(order)) {
            System.out.println("dishesIsAvailable after save" + order);
            acceptOrder(order.getOrderIdWaiterService());
            createOrderToDish(order);
        } else {
            System.out.println("dishesIsNotAvailable after save" + order);
            rejectOrder(order.getOrderIdWaiterService());
        }
    }

    @Override
    public void acceptOrder(Long orderId) {
        if (!kitchenOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenOrderRepository.updateStatusById(orderId, KitchenStatus.ACCEPTED);

    }

    @Override
    public void rejectOrder(Long orderId) {
        if (!kitchenOrderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenOrderRepository.updateStatusById(orderId, KitchenStatus.REJECTED);
        waiterFeignClient.sendCanceledOrderToWaiter(kitchenOrderDtoToWaiterOrderSendDtoMapper
                .map(getOrderById(orderId)));
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

    @Override
    public Boolean dishesIsAvailable(KitchenOrderDto order) {
        System.out.println(" before FindAll dishesIsAvailable: " + order);
        Set<String> dishDtoSet = kitchenDishRepository.findAllDistinct()
                .stream()
                .map(kitchenDishMapper::toDto)
                .map(DishDto::shortName)
                .collect(Collectors.toSet());

        System.out.println("dishIs available " + dishDtoSet);


        return dishDtoSet.containsAll(order.getOrderDishes()
                .stream()
                .map(DishDto::shortName)
                .collect(Collectors.toSet()));
    }

    @Override
    public void createOrderToDish(KitchenOrderDto order) {

        for (DishDto dishDto : order.getOrderDishes()) {
            OrderToDishDto orderToDishDto = new OrderToDishDto(order.getOrderIdWaiterService(),
                    getDishByShortName(dishDto.shortName()).id(),
                    dishDto.dishesNumber());
            System.out.println("for: orderToDishDto:" + orderToDishDto);

//            OrderToDish orderToDish = kitchenOrderToDishMapper.toEntity(orderToDishDto);
//
//            orderToDish.setDish(kitchenDishMapper.toEntity(getDishById(orderToDishDto.dishId())));
//            orderToDish.setOrder(kitchenOrderMapper.toEntity(getOrderById(orderToDishDto.kitchenOrderId())));

            OrderToDish orderToDish = OrderToDish.builder()
                    .id(new CompositeOrderToDishId(order.getOrderIdWaiterService(),
                            getDishByShortName(dishDto.shortName()).id()))
                    .dish(kitchenDishMapper.toEntity(getDishById(getDishByShortName(dishDto.shortName()).id())))
                    .order(kitchenOrderMapper.toEntity(getOrderById(order.getOrderIdWaiterService())))
                    .dishesNumber(dishDto.dishesNumber())
                    .build();
            System.out.println("orderToDish:" + orderToDish);
            kitchenOrderToDishRepository.save(orderToDish);
            System.out.println("kitchenOrderToDishRepository: after save" + orderToDishDto);
        }
    }

}
