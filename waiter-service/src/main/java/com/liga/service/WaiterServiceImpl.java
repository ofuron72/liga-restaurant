package com.liga.service;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderStatusDto;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.exceptions.StatusNotFoundException;
import com.liga.objects.OrderStatus;
import com.liga.repository.WaiterOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private final WaiterOrderMapper waiterOrderMapper;

    @Override
    public WaiterOrderDto getOrderById(Long id) {
        return Optional
                .ofNullable(waiterOrderMapper.getById(id))
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order with id %s not found", id)));
    }

    @Override
    public Set<WaiterOrderDto> getAllOrders() {
        return waiterOrderMapper.getAll();
    }

    @Override
    public WaiterOrderDto createOrder(WaiterOrderDto order) {

        order.setStatus(OrderStatus.ACCEPTED);
        order.setCreateDttm(OffsetDateTime.now());
        waiterOrderMapper.create(order);

        return order;
    }

    @Override
    public WaiterOrderStatusDto getOrderStatus(Long id) {
        return Optional
                .ofNullable(waiterOrderMapper.getOrderStatus(id))
                .orElseThrow(() -> new StatusNotFoundException(String.format("Status for order with id %s not found", id)));
    }

    @Override
    public void serveOrder(WaiterOrderDto order) {
        order.setStatus(OrderStatus.READY_TO_PICKUP);
        waiterOrderMapper.updateStatusOrder(order);
    }

    @Override
    public void cancelOrder(WaiterOrderDto order) {
        order.setStatus(OrderStatus.REJECTED_BY_THE_KITCHEN);
        waiterOrderMapper.updateStatusOrder(order);
    }
}
