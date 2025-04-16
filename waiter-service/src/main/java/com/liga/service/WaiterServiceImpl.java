package com.liga.service;

import com.liga.converter.WaiterOrderDtoToResponseMapper;
import com.liga.converter.WaiterOrderStatusDtoToResponseMapper;
import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderResponse;
import com.liga.dto.WaiterOrderStatusResponse;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.exceptions.StatusNotFoundException;
import com.liga.objects.OrderStatus;
import com.liga.repository.WaiterOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private final WaiterOrderMapper waiterOrderMapper;
    private final WaiterOrderDtoToResponseMapper waiterOrderDtoToResponseMapper;
    private final WaiterOrderStatusDtoToResponseMapper waiterOrderStatusDtoToResponseMapper;

    @Override
    public WaiterOrderResponse getOrderById(Long id) {
        return Optional
                .ofNullable(waiterOrderDtoToResponseMapper
                        .mapDtoToResponse(waiterOrderMapper.getById(id)))
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order with id %s not found", id)));
    }

    @Override
    public Set<WaiterOrderResponse> getAllOrders() {
        return waiterOrderMapper.getAll()
                .stream()
                .map(waiterOrderDtoToResponseMapper::mapDtoToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public WaiterOrderDto createOrder(WaiterOrderDto order) {

        order.setStatus(OrderStatus.ACCEPTED);
        order.setCreateDttm(OffsetDateTime.now());
        waiterOrderMapper.create(order);
        log.info("Order created: {}", order);

        return order;
    }

    @Override
    public WaiterOrderStatusResponse getOrderStatus(Long id) {
        return Optional
                .ofNullable(waiterOrderStatusDtoToResponseMapper.map(waiterOrderMapper.getOrderStatus(id)))
                .orElseThrow(() -> new StatusNotFoundException(String.format("Status for order with id %s not found", id)));
    }

    @Override
    public void serveOrder(WaiterOrderDto order) {
        order.setStatus(OrderStatus.READY_TO_PICKUP);
        waiterOrderMapper.updateStatusOrder(order);
        log.info("Order with id={} ready to pickup", order.getId());
    }

    @Override
    public void cancelOrder(WaiterOrderDto order) {
        order.setStatus(OrderStatus.REJECTED_BY_THE_KITCHEN);
        waiterOrderMapper.updateStatusOrder(order);
        log.info("Order with id={} rejected by the kitchen", order.getId());
    }
}
