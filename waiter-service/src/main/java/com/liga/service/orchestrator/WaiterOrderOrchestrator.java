package com.liga.service.orchestrator;

import com.liga.dto.KitchenOrderSendDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.exceptions.SendOrderFeignException;
import com.liga.integration.feign.KitchenFeignClient;
import com.liga.service.WaiterService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WaiterOrderOrchestrator {
    private final KitchenFeignClient kitchenFeignClient;
    private final WaiterService waiterService;

    public void saveAndSend(WaiterOrderDto waiterOrderDto) {
        WaiterOrderDto waiterOrderDtoWithId = waiterService.createOrder(waiterOrderDto);

        KitchenOrderSendDto kitchenOrderSendDto =
                new KitchenOrderSendDto(waiterOrderDto.getWaiterId(),
                        waiterOrderDtoWithId.getId(),
                        waiterOrderDtoWithId.getDishes());

        try {
            kitchenFeignClient.sendOrderToKitchen(kitchenOrderRequestDto);
        } catch (FeignException e) {
            throw new SendOrderFeignException("Failed sending order to the kitchen");
        }

    }
}
