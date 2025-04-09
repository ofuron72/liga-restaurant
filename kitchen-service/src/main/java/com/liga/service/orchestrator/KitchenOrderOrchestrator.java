package com.liga.service.orchestrator;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderRequestDto;
import com.liga.feign.WaiterFeignClient;
import com.liga.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KitchenOrderOrchestrator {

    private final WaiterFeignClient waiterFeignClient;
    private final KitchenService kitchenService;

    public void setCookedAndSendOrder(Long orderId){

        KitchenOrderDto kitchenOrderDto = kitchenService.getOrderById(orderId);

        WaiterOrderRequestDto orderForWaiterService = new WaiterOrderRequestDto(kitchenOrderDto.getWaiterOrderNo(),
                kitchenOrderDto.getId());

        kitchenService.setStatusCooked(orderId);

        waiterFeignClient.sendCookedOrderToWaiter(orderForWaiterService);
    }

}
