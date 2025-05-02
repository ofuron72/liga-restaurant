package com.liga.entities;

import com.liga.dto.DishSendDto;
import com.liga.objects.OrderStatus;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

/**
 * DTO, представляющий заказ, в сервисе официанта.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class WaiterOrderEntity {
    private Long id;

    private OrderStatus status;

    private OffsetDateTime createDttm;

    private Long waiterId;

    private String tableNo;

    private Set<DishSendDto> dishes;
}
