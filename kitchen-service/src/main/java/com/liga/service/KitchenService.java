package com.liga.service;


import com.liga.dto.DishDto;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderResponse;

import java.util.Set;

public interface KitchenService {
    Set<KitchenOrderResponse> getAllOrders();

    void acceptOrder(Long orderId);

    void rejectOrder(Long orderId);

    void setStatusReady(Long orderId);

    void createOrder(KitchenOrderDto order);

    KitchenOrderDto getOrderById(Long orderId);

    Boolean dishesIsAvailable(KitchenOrderDto order);

    void createOrderToDish(KitchenOrderDto order);



}
