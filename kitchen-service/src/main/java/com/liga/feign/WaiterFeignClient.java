package com.liga.feign;

import com.liga.dto.WaiterOrderSendDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "waiter-service", url = "${spring.cloud.openfeign.client.config.waiter-service.url}")
public interface WaiterFeignClient {
    @PostMapping("/api/waiter/orders/cooked")
    ResponseEntity<Void> sendCookedOrderToWaiter(@RequestBody WaiterOrderSendDto waiterOrderRequestDto);


    @PostMapping("/api/waiter/orders/rejected")
    ResponseEntity<Void> sendCanceledOrderToWaiter(@RequestBody WaiterOrderSendDto waiterOrderSendDto);
}
