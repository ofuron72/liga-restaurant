package com.liga.dto;

import com.liga.objects.KitchenStatus;

/**
 * Ответ, содержащий информацию о заказе на кухне.
 * Используется для возврата клиенту сведений о заказе.
 */
public record KitchenOrderResponse(Long id,
                                   Long waiterOrderNo,
                                   KitchenStatus status,
                                   Long orderIdWaiterService) {
}
