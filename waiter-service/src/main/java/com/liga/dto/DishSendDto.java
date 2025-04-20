package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Представляет информацию о блюде, включённом в заказ,
 * используемую при передаче данных между сервисами.
 */
public record DishSendDto(
        @JsonProperty("shortName") String shortName,
        @JsonProperty("dishesNumber") Long dishesNumber
) {
}
