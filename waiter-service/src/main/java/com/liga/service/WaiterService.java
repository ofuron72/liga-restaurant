package com.liga.service;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderResponse;
import com.liga.dto.WaiterOrderStatusDto;
import com.liga.dto.WaiterOrderStatusResponse;

import java.util.Set;

public interface WaiterService {
    WaiterOrderResponse getOrderById(Long id);

    Set<WaiterOrderResponse> getAllOrders();

    WaiterOrderDto createOrder(WaiterOrderDto order);

    WaiterOrderStatusResponse getOrderStatus(Long id);

    void serveOrder(WaiterOrderDto order);

    void cancelOrder(WaiterOrderDto order);
}
