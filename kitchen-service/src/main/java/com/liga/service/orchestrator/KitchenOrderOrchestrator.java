package com.liga.service.orchestrator;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderRequestDto;
import com.liga.exceptions.SendOrderFeignException;
import com.liga.feign.WaiterFeignClient;
import com.liga.service.KitchenService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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

        try {
            waiterFeignClient.sendCookedOrderToWaiter(orderForWaiterService);
        } catch (FeignException ex) {
            throw new SendOrderFeignException("Error sending cooked order to waiter");

        }
    }

}
