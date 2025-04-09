package com.liga.controllers;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderStatusDto;
import com.liga.service.WaiterService;
import com.liga.service.facade.WaiterOrderFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waiter/orders")
@RequiredArgsConstructor
public class WaiterOrderController {
    private final WaiterService waiterService;
    private final WaiterOrderFacade waiterOrderFacade;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid WaiterOrderDto waiterOrderDto) {
        waiterOrderFacade.saveAndSend(waiterOrderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WaiterOrderDto>> getAllOrders() {
        List<WaiterOrderDto> ordersDto = waiterService.getAllOrders();
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

}
