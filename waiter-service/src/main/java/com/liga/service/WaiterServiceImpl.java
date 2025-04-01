package com.liga.service;

import com.liga.dto.OrderDto;
import com.liga.dto.OrderStatusDto;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.repository.WaiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private final WaiterRepository waiterRepository;

    @Override
    public OrderDto getOrderById(Long id) {
        return waiterRepository.getOrderById(id);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return waiterRepository.getAllOrders();
    }

    @Override
    public void createOrder(OrderDto order) {
        order.setOrderTime(LocalDateTime.now());
        waiterRepository.create(order);
    }

    @Override
    public OrderStatusDto getOrderStatus(Long id) {
        return waiterRepository.getOrderStatus(id);
    }
}
