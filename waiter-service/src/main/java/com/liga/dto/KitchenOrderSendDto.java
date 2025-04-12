package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record KitchenOrderSendDto(
        @JsonProperty("waiterOrderNo") Long waiterOrderNo,
        @JsonProperty("orderIdWaiterService") Long orderIdWaiterService,
        @JsonProperty("dishes") Set<DishSendDto> dishes) {
}
