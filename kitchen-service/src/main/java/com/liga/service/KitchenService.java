package com.liga.service;


import com.liga.dto.KitchenOrderDto;
import java.util.List;

public interface KitchenService {
    List<KitchenOrderDto> getAllOrders();

    void acceptOrder(Long orderId);

    void rejectOrder(Long orderId);

    void setStatusCooked(Long orderId);

    void createOrder(KitchenOrderDto order);

    KitchenOrderDto getOrderById(Long orderId);


}
