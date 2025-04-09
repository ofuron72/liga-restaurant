package com.liga.service;
import com.liga.converter.KitchenOrderMapper;
import com.liga.dto.KitchenOrderDto;
import com.liga.entities.KitchenOrder;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.objects.KitchenStatus;
import com.liga.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {
    private final KitchenRepository kitchenRepository;
    private final KitchenOrderMapper kitchenOrderMapper;

    @Override
    public List<KitchenOrderDto> getAllOrders() {
        List<KitchenOrder> listEntities = kitchenRepository.findAll();

        return listEntities.stream()
                .map(kitchenOrderMapper::toDto)
                .toList();
    }

    @Override
    public void createOrder(KitchenOrderDto order) {
        order.setStatus(KitchenStatus.CREATED);
        kitchenRepository.save(kitchenOrderMapper.toEntity(order));
    }

    @Override
    public void acceptOrder(Long orderId) {
        if (!kitchenRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenRepository.updateStatusById(orderId, KitchenStatus.ACCEPTED);

    }

    @Override
    public void rejectOrder(Long orderId) {
        if (!kitchenRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenRepository.updateStatusById(orderId, KitchenStatus.REJECTED);
    }

    @Override
    public void setStatusCooked(Long orderId) {
        if (!kitchenRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", orderId));
        }
        kitchenRepository.updateStatusById(orderId, KitchenStatus.COOKED);
    }

    public KitchenOrderDto getOrderById(Long orderId) {
        return kitchenOrderMapper.toDto(kitchenRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException(String.format("Order with id %s not found", orderId))));
    }
}
