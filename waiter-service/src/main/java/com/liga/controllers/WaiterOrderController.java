package com.liga.controllers;

import com.liga.dto.OrderDto;
import com.liga.dto.OrderStatusDto;
import com.liga.service.WaiterService;
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

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        waiterService.createOrder(orderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<OrderDto> ordersDto = waiterService.getAllOrders();
        return new ResponseEntity<>(ordersDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        OrderDto orderDto = waiterService.getOrderById(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> getOrderStatusById(@PathVariable Long id) {
        OrderStatusDto orderStatusDto = waiterService.getOrderStatus(id);
        return new ResponseEntity<>(orderStatusDto, HttpStatus.OK);

    }

}
