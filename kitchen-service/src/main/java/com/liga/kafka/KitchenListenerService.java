package com.liga.kafka;

import com.liga.converter.KitchenOrderDtoMapper;
import com.liga.dto.CreateOrderEvent;
import com.liga.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@KafkaListener(topics = "${kafka.topic.name}", groupId = "kitchen-group")
public class KitchenListenerService {
    private final KitchenService kitchenService;
    private final KitchenOrderDtoMapper kitchenOrderDtoMapper;

    @KafkaHandler
    public void handleOrder(CreateOrderEvent event) {
        kitchenService.createOrder(kitchenOrderDtoMapper.toDto(event));
    }
}
