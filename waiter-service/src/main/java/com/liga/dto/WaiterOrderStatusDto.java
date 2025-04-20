package com.liga.dto;

import com.liga.objects.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO, представляющий статус заказа, в сервисе официанта.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaiterOrderStatusDto {
    OrderStatus status;
}
