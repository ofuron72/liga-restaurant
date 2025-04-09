package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WaiterOrderRequestDto(
        @JsonProperty("waiterId") Long waiterId,
        @JsonProperty("id") Long orderIdWaiterService) {
}
