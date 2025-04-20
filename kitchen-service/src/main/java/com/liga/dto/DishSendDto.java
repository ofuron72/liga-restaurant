package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO для передачи информации о блюде в заказе.
 */
public record DishSendDto(
        @JsonProperty("shortName") String shortName,
        @JsonProperty("dishesNumber") Long dishesNumber
) {
}
