package com.liga.service.orchestrator;

import com.liga.converter.KitchenOrderDtoToWaiterOrderSendDtoMapper;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderSendDto;
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
    private final KitchenOrderDtoToWaiterOrderSendDtoMapper kitchenOrderDtoToWaiterOrderSendDtoMapper;

    public void setCookedAndSendOrder(Long orderId){

        KitchenOrderDto kitchenOrderDto = kitchenService.getOrderById(orderId);

        WaiterOrderSendDto orderForWaiterService =
                kitchenOrderDtoToWaiterOrderSendDtoMapper.map(kitchenOrderDto);

        kitchenService.setStatusCooked(orderId);

        try {
            waiterFeignClient.sendCookedOrderToWaiter(orderForWaiterService);
        } catch (FeignException ex) {
            throw new SendOrderFeignException("Error sending cooked order to waiter");

        }
    }

}
