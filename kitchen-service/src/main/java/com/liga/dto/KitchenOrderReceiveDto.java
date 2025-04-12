package com.liga.dto;

import java.util.Set;

public record KitchenOrderReceiveDto(
        Long waiterOrderNo,
        Long orderIdWaiterService,
        Set<DishDto> dishes) {
}
