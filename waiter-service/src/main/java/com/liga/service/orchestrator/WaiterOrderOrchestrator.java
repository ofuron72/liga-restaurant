package com.liga.service.orchestrator;

import com.liga.dto.KitchenOrderRequestDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.exceptions.SendOrderFeignException;
import com.liga.integration.feign.KitchenFeignClient;
import com.liga.service.WaiterService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WaiterOrderOrchestrator {
    private final KafkaTemplate<String, CreateOrderEvent> kafkaTemplate;

    private final WaiterService waiterService;
    private final WaiterOrderDtoToKitchenSendDtoMapper waiterOrderDtoToKitchenSendDtoMapper;
    @Value("${kafka.topic.name}")
    private String topicName;

    public void saveAndSend(WaiterOrderDto waiterOrderDto) {
        WaiterOrderDto waiterOrderDtoWithId = waiterService.createOrder(waiterOrderDto);

        CreateOrderEvent createOrderEvent = waiterOrderDtoToKitchenSendDtoMapper.map(waiterOrderDtoWithId);

        kafkaTemplate.send(topicName, createOrderEvent);


    }
}
