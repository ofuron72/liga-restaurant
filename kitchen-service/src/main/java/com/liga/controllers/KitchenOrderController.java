package com.liga.controllers;

import com.liga.dto.KitchenStatusDto;
import com.liga.dto.OrderDto;
import com.liga.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen/orders")
@RequiredArgsConstructor
public class KitchenOrderController {
    private final KitchenService kitchenService;

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<OrderDto> orders = kitchenService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/{id}/setAccess")
    public ResponseEntity<?> setAccessStatus(@PathVariable Long id) {
        kitchenService.acceptOrder(id);
        return new ResponseEntity<>(new KitchenStatusDto(String
                .format("status order with id: %d changed -> ACCESS", id))
                , HttpStatus.OK);
    }

    @PostMapping("/{id}/setReady")
    public ResponseEntity<?> setReadyStatus(@PathVariable Long id) {
        kitchenService.setStatusReady(id);
        return new ResponseEntity<>(new KitchenStatusDto(String
                .format("status order with id: %d changed -> READY", id))
                , HttpStatus.OK);
    }

    @PostMapping("/{id}/setReject")
    public ResponseEntity<?> setRejectStatus(@PathVariable Long id) {
        kitchenService.rejectOrder(id);
        return new ResponseEntity<>(new KitchenStatusDto(String
                .format("status order with id: %d changed -> REJECT", id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        kitchenService.createOrder(orderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
