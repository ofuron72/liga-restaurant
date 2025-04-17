package com.liga.service.orchestrator;

import com.liga.converter.WaiterOrderDtoMapper;
import com.liga.converter.WaiterOrderDtoToKitchenSendDtoMapper;
import com.liga.dto.CreateOrderEvent;
import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.service.WaiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaiterOrderOrchestrator {
    private final WaiterOrderDtoMapper waiterOrderDtoMapper;
    private final KafkaTemplate<String, CreateOrderEvent> kafkaTemplate;
    private final WaiterService waiterService;
    private final WaiterOrderDtoToKitchenSendDtoMapper waiterOrderDtoToKitchenSendDtoMapper;
    @Value("${kafka.topic.name}")
    private String topicName;

    public void saveAndSend(WaiterOrderCreateRequestDto waiterOrderDto) {
        WaiterOrderDto waiterOrderDtoWithId = waiterService
                .createOrder(waiterOrderDtoMapper.toWaiterOrderDto(waiterOrderDto));

        CreateOrderEvent createOrderEvent = waiterOrderDtoToKitchenSendDtoMapper
                .map(waiterOrderDtoWithId);

        log.debug("trying to send order: {}", createOrderEvent);
        kafkaTemplate.send(topicName, createOrderEvent);
        log.info("order sent to {}", createOrderEvent);
    }
}
