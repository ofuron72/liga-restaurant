package com.liga.service.orchestrator;

import com.liga.dto.KitchenOrderRequestDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.integration.feign.KitchenFeignClient;
import com.liga.service.WaiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaiterOrderOrchestrator {
    private final KitchenFeignClient kitchenFeignClient;
    private final WaiterService waiterService;

    public void saveAndSend(WaiterOrderDto waiterOrderDto) {
        WaiterOrderDto waiterOrderDtoWithId = waiterService.createOrder(waiterOrderDto);

        KitchenOrderRequestDto kitchenOrderRequestDto =
                new KitchenOrderRequestDto(waiterOrderDto.getWaiterId(), waiterOrderDtoWithId.getId());


        kitchenFeignClient.sendOrderToKitchen(kitchenOrderRequestDto);
    }
}
