package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KitchenOrderRequestDto(
        @JsonProperty("waiterOrderNo") Long waiterOrderNo,
        @JsonProperty("orderIdWaiterService") Long orderIdWaiterService) {
}
