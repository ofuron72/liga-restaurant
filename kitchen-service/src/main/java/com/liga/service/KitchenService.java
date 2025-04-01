package com.liga.service;


import com.liga.dto.OrderDto;

import java.util.List;

public interface KitchenService {
    List<OrderDto> getAllOrders();

    void acceptOrder(Long orderId);

    void rejectOrder(Long orderId);

    void setStatusReady(Long orderId);

    void createOrder(OrderDto order);


}
