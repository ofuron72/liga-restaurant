package com.liga.dto;

import com.liga.objects.OrderStatus;
import java.time.OffsetDateTime;

/**
 * response DTO, содержащий информацию о заказе.
 */
public record WaiterOrderResponse(Long id,
                                  OrderStatus status,
                                  OffsetDateTime createDttm,
                                  Long waiterId,
                                  String tableNo
) {
}
