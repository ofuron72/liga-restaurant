package com.liga.dto;

import com.liga.objects.KitchenStatus;

public record KitchenOrderResponse(Long id,
                                   Long waiterOrderNo,
                                   KitchenStatus status,
                                   Long orderIdWaiterService) {
}
