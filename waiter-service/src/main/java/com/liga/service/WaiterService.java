package com.liga.service;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderStatusDto;

import java.util.List;

public interface WaiterService {
    WaiterOrderDto getOrderById(Long id);

    List<WaiterOrderDto> getAllOrders();

    void createOrder(WaiterOrderDto order);

    WaiterOrderStatusDto getOrderStatus(Long id);
}
