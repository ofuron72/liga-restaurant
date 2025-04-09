package com.liga.controllers;

import com.liga.dto.KitchenOrderDto;
import com.liga.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/integration-api/orders")
public class KitchenFeignController {
    private final KitchenService kitchenService;

    @PostMapping
    public ResponseEntity<Void> receiveOrderFromWaiter(@RequestBody KitchenOrderDto orderDto) {
        kitchenService.createOrder(orderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
