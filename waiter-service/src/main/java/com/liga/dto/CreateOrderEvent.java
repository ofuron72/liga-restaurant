package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * событие создания заказа, отправляемое из сервиса официанта в кухонный сервис
 * через kafka.
 */
public record CreateOrderEvent(
        @JsonProperty("waiterOrderNo") Long waiterOrderNo,
        @JsonProperty("orderIdWaiterService") Long orderIdWaiterService,
        @JsonProperty("dishes") List<DishSendDto> dishes) {
}
