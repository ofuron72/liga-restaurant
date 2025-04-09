package com.liga.service;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderStatusDto;

import java.util.List;

public interface WaiterService {
    WaiterOrderDto getOrderById(Long id);

    List<WaiterOrderDto> getAllOrders();

    WaiterOrderDto createOrder(WaiterOrderDto order);

    WaiterOrderStatusDto getOrderStatus(Long id);

    void serveOrder(WaiterOrderDto order);
}
