package com.liga.dto;

import java.util.List;

public record KitchenOrderReceiveDto(
        Long waiterOrderNo,
        Long orderIdWaiterService,
        List<DishDto> dishes) {
}
