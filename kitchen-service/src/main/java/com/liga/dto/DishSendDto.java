package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DishSendDto(
        @JsonProperty("shortName") String shortName,
        @JsonProperty("dishesNumber") Long dishesNumber
) {
}
