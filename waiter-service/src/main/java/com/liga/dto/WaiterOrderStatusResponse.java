package com.liga.dto;

import com.liga.objects.OrderStatus;

/**
 * Ответный DTO, содержащий статус заказа, оформленного официантом.
 */
public record WaiterOrderStatusResponse(OrderStatus status) {
}
