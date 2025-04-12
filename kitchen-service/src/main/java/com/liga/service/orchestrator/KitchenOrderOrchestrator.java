package com.liga.service.orchestrator;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderSendDto;
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

        WaiterOrderSendDto orderForWaiterService = new WaiterOrderSendDto(kitchenOrderDto.getWaiterOrderNo(),
                kitchenOrderDto.getId(), kitchenOrderDto.getDishes());

        kitchenService.setStatusCooked(orderId);

        waiterFeignClient.sendCookedOrderToWaiter(orderForWaiterService);
    }


}
