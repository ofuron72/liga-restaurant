package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Представляет информацию о блюде, включённом в заказ,
 * используемую при передаче данных между сервисами.
 */
public record DishSendDto(
        @JsonProperty("shortName") String shortName,
        @Positive(message = "dishes number must be positive")
        @NotNull(message = "dishes number must not be null")
        @JsonProperty("dishesNumber") Long dishesNumber
) {
}
