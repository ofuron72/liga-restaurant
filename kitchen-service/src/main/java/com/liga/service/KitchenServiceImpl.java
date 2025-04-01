package com.liga.service;

import com.liga.dto.OrderDto;
import com.liga.objects.KitchenStatus;
import com.liga.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {
    private final KitchenRepository kitchenRepository;

    @Override
    public List<OrderDto> getAllOrders() {
        return kitchenRepository.getAllOrders();
    }

    @Override
    public void createOrder(OrderDto order) {
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(KitchenStatus.CREATED);
        kitchenRepository.createOrder(order);
    }

    @Override
    public void acceptOrder(Long orderId) {
        kitchenRepository.setStatus(orderId, KitchenStatus.ACCEPTED);
    }

    @Override
    public void rejectOrder(Long orderId) {
        kitchenRepository.setStatus(orderId, KitchenStatus.REJECTED);

    }

    @Override
    public void setStatusReady(Long orderId) {
        kitchenRepository.setStatus(orderId, KitchenStatus.READY);
    }
}
