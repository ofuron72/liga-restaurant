package com.liga.controllers;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.ResponseDto;
import com.liga.service.KitchenService;
import com.liga.service.orchestrator.KitchenOrderOrchestrator;
import jakarta.validation.Valid;
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
    private final KitchenOrderOrchestrator kitchenOrderOrchestrator;

    @GetMapping
    public ResponseEntity<List<KitchenOrderDto>> getAllOrders() {
        List<KitchenOrderDto> orders = kitchenService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/{id}/setAccess")
    public ResponseEntity<ResponseDto> setAccessStatus(@PathVariable Long id) {
        kitchenService.acceptOrder(id);
        return new ResponseEntity<>(new ResponseDto(String
                .format("status order with id: %d changed -> ACCESS", id))
                , HttpStatus.OK);
    }

    @PostMapping("/{id}/setReady")
    public ResponseEntity<ResponseDto> setReadyStatus(@PathVariable Long id) {

        kitchenOrderOrchestrator.setCookedAndSendOrder(id);
        return new ResponseEntity<>(new ResponseDto(String
                .format("status order with id: %d changed -> READY", id))
                , HttpStatus.OK);
    }

    @PostMapping("/{id}/setReject")
    public ResponseEntity<ResponseDto> setRejectStatus(@PathVariable Long id) {
        kitchenService.rejectOrder(id);
        return new ResponseEntity<>(new ResponseDto(String
                .format("status order with id: %d changed -> REJECT", id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid KitchenOrderDto kitchenOrderDto) {
        kitchenService.createOrder(kitchenOrderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
