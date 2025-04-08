package com.liga.service;


import com.liga.dto.KitchenOrderDto;
import java.util.List;

public interface KitchenService {
    List<KitchenOrderDto> getAllOrders();

    void acceptOrder(Long orderId);

    void rejectOrder(Long orderId);

    void setStatusReady(Long orderId);

    void createOrder(KitchenOrderDto order);


}
