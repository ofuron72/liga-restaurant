package com.liga.service;

import com.liga.dto.WaiterMenuItemResponse;
import com.liga.entities.WaiterOrderEntity;
import com.liga.dto.WaiterOrderResponse;
import com.liga.dto.WaiterOrderStatusResponse;

import java.util.Set;

public interface WaiterService {
    WaiterOrderResponse getOrderById(Long id);

    Set<WaiterOrderResponse> getAllOrders();

    WaiterOrderEntity createOrder(WaiterOrderEntity order);

    WaiterOrderStatusResponse getOrderStatus(Long id);

    void serveOrder(WaiterOrderEntity order);

    void cancelOrder(WaiterOrderEntity order);

    Set<WaiterMenuItemResponse> getAllMenuItem();
}
