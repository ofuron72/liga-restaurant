package com.liga.controllers;

import com.liga.converter.WaiterOrderDtoMapper;
import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderStatusDto;
import com.liga.service.WaiterService;
import com.liga.service.orchestrator.WaiterOrderOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/waiter/orders")
@RequiredArgsConstructor
public class WaiterOrderController {
    private final WaiterService waiterService;
    private final WaiterOrderOrchestrator waiterOrderOrchestrator;
    private final WaiterOrderDtoMapper waiterOrderDtoMapper;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid WaiterOrderCreateRequestDto waiterOrderDto) {
        System.out.println(waiterOrderDto);
        System.out.println(waiterOrderDtoMapper
                .toWaiterOrderDto(waiterOrderDto));
        waiterOrderOrchestrator.saveAndSend(waiterOrderDtoMapper
                .toWaiterOrderDto(waiterOrderDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<WaiterOrderDto>> getAllOrders() {
        Set<WaiterOrderDto> ordersDto = waiterService.getAllOrders();
        return new ResponseEntity<>(ordersDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaiterOrderDto> getOrderById(@PathVariable Long id) {
        WaiterOrderDto waiterOrderDto = waiterService.getOrderById(id);
        return new ResponseEntity<>(waiterOrderDto, HttpStatus.OK);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<WaiterOrderStatusDto> getOrderStatusById(@PathVariable Long id) {
        WaiterOrderStatusDto waiterOrderStatusDto = waiterService.getOrderStatus(id);
        return new ResponseEntity<>(waiterOrderStatusDto, HttpStatus.OK);
    }


    @PostMapping("/cooked")
    public ResponseEntity<Void> receiveCookedOrderFromKitchen(@RequestBody WaiterOrderDto waiterOrderDto) {
        waiterService.serveOrder(waiterOrderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rejected")
    public ResponseEntity<Void> receiveRejectedOrderFromKitchen(@RequestBody WaiterOrderDto waiterOrderDto) {
        waiterService.cancelOrder(waiterOrderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
