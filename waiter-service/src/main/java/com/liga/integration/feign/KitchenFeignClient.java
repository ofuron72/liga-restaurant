package com.liga.integration.feign;

import com.liga.dto.KitchenOrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "kitchen-service", url = "${spring.cloud.openfeign.client.config.kitchen-service.url}")
public interface KitchenFeignClient {

    @PostMapping("/api/kitchen/orders/receive")
    ResponseEntity<Void> sendOrderToKitchen(@RequestBody KitchenOrderRequestDto kitchenOrderRequestDto);
}
