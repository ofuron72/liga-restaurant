package com.liga.repository;

import com.liga.dto.OrderDto;
import com.liga.dto.OrderStatusDto;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.exceptions.StatusNotFoundException;
import com.liga.objects.OrderStatus;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

@Repository
public class WaiterRepository {
    private final Map<Long, OrderDto> orders = new HashMap<Long, OrderDto>();

    public OrderDto getOrderById(Long id) {
        return Optional.ofNullable(orders.get(id))
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order with id %s not found", id)));
    }

    public List<OrderDto> getAllOrders() {
        return new ArrayList<OrderDto>(orders.values());
    }

    public void create(OrderDto order) {
        order.setId((long) (orders.size() + 1));
        orders.put(order.getId(), order);
    }

    public OrderStatusDto getOrderStatus(Long id) {
        OrderStatus orderStatus = Optional.ofNullable(orders.get(id).getStatus())
                .orElseThrow(() -> new StatusNotFoundException(String.format("Status for order with id %s not found", id)));
        return new OrderStatusDto(orderStatus);
    }


}
