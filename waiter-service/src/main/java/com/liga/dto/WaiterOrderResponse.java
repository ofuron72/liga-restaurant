package com.liga.dto;

import com.liga.objects.OrderStatus;

import java.time.OffsetDateTime;

public record WaiterOrderResponse(Long id,
                                  OrderStatus status,
                                  OffsetDateTime createDttm,
                                  Long waiterId,
                                  String tableNo
) {
}
