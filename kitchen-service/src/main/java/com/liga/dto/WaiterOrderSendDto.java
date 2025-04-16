package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WaiterOrderSendDto(
        @JsonProperty("waiterId") Long waiterId,
        @JsonProperty("id") Long orderIdWaiterService,
        @JsonProperty("dishes") List<DishDto> dishes) {
}
