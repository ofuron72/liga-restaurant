package com.liga.service;

import com.liga.dto.WaiterMenuItemResponse;
import com.liga.entities.WaiterOrder;
import com.liga.dto.WaiterOrderResponse;
import com.liga.dto.WaiterOrderStatusResponse;

import java.util.Set;

public interface WaiterService {
    WaiterOrderResponse getOrderById(Long id);

    Set<WaiterOrderResponse> getAllOrders();

    WaiterOrder createOrder(WaiterOrder order);

    WaiterOrderStatusResponse getOrderStatus(Long id);

    void serveOrder(WaiterOrder order);

    void cancelOrder(WaiterOrder order);

    Set<WaiterMenuItemResponse> getAllMenuItem();
}
