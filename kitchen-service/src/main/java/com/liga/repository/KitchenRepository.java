package com.liga.repository;

import com.liga.dto.OrderDto;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.objects.KitchenStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class KitchenRepository {
    private final Map<Long, OrderDto> orders = new HashMap<Long, OrderDto>();

    public List<OrderDto> getAllOrders() {
        return new ArrayList<OrderDto>(orders.values());
    }

    public void setStatus(Long id, KitchenStatus status) {
        Optional.ofNullable(orders.get(id))
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order with id %s not found", id)))
                .setStatus(status);
    }

    public void createOrder(OrderDto orderDto) {
        orderDto.setId((long) (orders.size() + 1));
        orders.put(orderDto.getId(), orderDto);
    }


}
