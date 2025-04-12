package com.liga.service;

import com.liga.converter.KitchenDishMapper;
import com.liga.converter.KitchenOrderMapper;
import com.liga.converter.KitchenOrderToDishMapper;
import com.liga.dto.DishDto;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.OrderToDishDto;
import com.liga.entities.KitchenOrder;
import com.liga.exceptions.DishNotFoundException;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.objects.KitchenStatus;
import com.liga.repository.KitchenDishRepository;
import com.liga.repository.KitchenOrderRepository;
import com.liga.repository.KitchenOrderToDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {
    private final KitchenOrderRepository kitchenOrderRepository;
    private final KitchenOrderMapper kitchenOrderMapper;
    private final KitchenDishRepository kitchenDishRepository;
    private final KitchenDishMapper kitchenDishMapper;
    private final KitchenOrderToDishRepository kitchenOrderToDishRepository;
    private final KitchenOrderToDishMapper kitchenOrderToDishMapper;

    @Override
    public Set<KitchenOrderDto> getAllOrders() {
        Set<KitchenOrder> setEntities = kitchenOrderRepository.findAllDistinct();

        return setEntities.stream()
                .map(kitchenOrderMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void createOrder(KitchenOrderDto order) {
        order.setStatus(KitchenStatus.CREATED);

        System.out.println("++++++++++++++++++");
        System.out.println(order);
        order = kitchenOrderMapper.toDto(kitchenOrderRepository.save(kitchenOrderMapper.toEntity(order)));
        System.out.println("================");
        System.out.println(order);

        if (dishesIsAvailable(order)) {
            acceptOrder(order.getOrderIdWaiterService());
            createOrderToDish(order);
        } else {
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
    }

    @Override
    public void setStatusCooked(Long orderId) {
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
    public Boolean dishesIsAvailable(KitchenOrderDto order) {
        Set<String> dishDtoSet = kitchenDishRepository.findAllDistinct()
                .stream()
                .map(kitchenDishMapper::toDto)
                .map(DishDto::shortName)
                .collect(Collectors.toSet());

        System.out.println(dishDtoSet);
        System.out.println("===================");
        System.out.println(order.getDishes()
                .stream()
                .map(DishDto::shortName)
                .collect(Collectors.toSet()));
//        System.out.println(dishesIsAvailable(order));

        return dishDtoSet.containsAll(order.getDishes()
                .stream()
                .map(DishDto::shortName)
                .collect(Collectors.toSet()));//возможно стоит использовать Set
    }

    @Override
    public void createOrderToDish(KitchenOrderDto order) {
        System.out.println("=======");
        System.out.println(order);
        System.out.println("============");
        for (DishDto dishDto : order.getDishes()) {
            OrderToDishDto orderToDishDto = new OrderToDishDto(order.getOrderIdWaiterService(),
                    getDishByShortName(dishDto.shortName()).id(),
                    dishDto.dishesNumber());
            System.out.println(orderToDishDto);
            kitchenOrderToDishRepository.save(kitchenOrderToDishMapper.toEntity(orderToDishDto));
        }
    }
}
