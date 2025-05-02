package com.liga.dto;

import com.liga.objects.OrderStatus;

import java.time.OffsetDateTime;
import java.util.Set;

public record WaiterOrderDto(Long id,
                             OrderStatus status,
                             OffsetDateTime createDttm,
                             Long waiterId,
                             String tableNo,
                             Set<DishSendDto>dishes) {
}
