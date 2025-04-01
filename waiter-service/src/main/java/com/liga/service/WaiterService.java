package com.liga.service;

import com.liga.dto.OrderDto;
import com.liga.dto.OrderStatusDto;

import java.util.List;

public interface WaiterService {
    OrderDto getOrderById(Long id);

    List<OrderDto> getAllOrders();

    void createOrder(OrderDto order);

    OrderStatusDto getOrderStatus(Long id);
}
