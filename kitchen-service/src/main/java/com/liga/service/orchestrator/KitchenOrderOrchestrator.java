package com.liga.service.orchestrator;

import com.liga.converter.KitchenOrderDtoToWaiterOrderSendDtoMapper;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderSendDto;
import com.liga.exceptions.SendOrderFeignException;
import com.liga.feign.WaiterFeignClient;
import com.liga.service.KitchenService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис, который управляет оркестрацией обработки заказа на кухне.
 */
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class KitchenOrderOrchestrator {

    private final WaiterFeignClient waiterFeignClient;
    private final KitchenService kitchenService;
    private final KitchenOrderDtoToWaiterOrderSendDtoMapper kitchenOrderDtoToWaiterOrderSendDtoMapper;

    /**
     * Устанавливает статус заказа как {@code READY} и отправляет обновленную информацию о заказе в сервис официантов.
     */
    public void setCookedAndSendOrder(Long orderId){

        KitchenOrderDto kitchenOrderDto = kitchenService.getOrderById(orderId);

        WaiterOrderSendDto orderForWaiterService =
                kitchenOrderDtoToWaiterOrderSendDtoMapper.map(kitchenOrderDto);

        kitchenService.setStatusReady(orderId);

        try {
            waiterFeignClient.sendCookedOrderToWaiter(orderForWaiterService);
            log.info("Sent order to waiter");
        } catch (FeignException ex) {
            log.error("Error sending cooked order: {} to waiter", orderForWaiterService);
            throw new SendOrderFeignException("Error sending cooked order to waiter");
        }
    }
}
