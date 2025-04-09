package com.liga.integration.feign;

import com.liga.dto.KitchenOrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "kitchen-service", url = "localhost:8080")
public interface KitchenFeignClient {

    @PostMapping("/integration-api/orders")
    ResponseEntity<Void> sendOrderToKitchen(@RequestBody KitchenOrderRequestDto kitchenOrderRequestDto);
}
