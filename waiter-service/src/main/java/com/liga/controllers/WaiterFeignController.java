package com.liga.controllers;

import com.liga.dto.WaiterOrderDto;
import com.liga.service.WaiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/integration-api/orders")
public class WaiterFeignController {

    private final WaiterService waiterService;
    @PostMapping("/cooked")
    public ResponseEntity<Void> receiveCookedOrderFromKitchen(@RequestBody WaiterOrderDto waiterOrderDto) {
        waiterService.serveOrder(waiterOrderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
