package com.liga.service;


import com.liga.dto.KitchenOrderDto;
import java.util.List;
import java.util.Set;

public interface KitchenService {
    Set<KitchenOrderDto> getAllOrders();

    void acceptOrder(Long orderId);

    void rejectOrder(Long orderId);

    void setStatusCooked(Long orderId);

    void createOrder(KitchenOrderDto order);

    KitchenOrderDto getOrderById(Long orderId);

    Boolean dishesIsAvailable(KitchenOrderDto order);

    void createOrderToDish(KitchenOrderDto order);


}
