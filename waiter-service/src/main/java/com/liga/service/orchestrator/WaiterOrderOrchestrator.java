package com.liga.service.orchestrator;

import com.liga.converter.WaiterOrderDtoToKitchenSendDtoMapper;
import com.liga.dto.KitchenOrderSendDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.exceptions.SendOrderFeignException;
import com.liga.integration.feign.KitchenFeignClient;
import com.liga.service.WaiterService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaiterOrderOrchestrator {
    private final KitchenFeignClient kitchenFeignClient;
    private final WaiterService waiterService;
    private final WaiterOrderDtoToKitchenSendDtoMapper waiterOrderDtoToKitchenSendDtoMapper;

    public void saveAndSend(WaiterOrderDto waiterOrderDto) {
        WaiterOrderDto waiterOrderDtoWithId = waiterService.createOrder(waiterOrderDto);

        KitchenOrderSendDto kitchenOrderSendDto = waiterOrderDtoToKitchenSendDtoMapper.map(waiterOrderDtoWithId);

        try {
            kitchenFeignClient.sendOrderToKitchen(kitchenOrderSendDto);
        } catch (FeignException e) {
            throw new SendOrderFeignException("Failed sending order to the kitchen");
        }

    }
}
