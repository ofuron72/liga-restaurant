package com.liga.dto;

import com.liga.objects.OrderStatus;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class WaiterOrderDto {
    private Long id;

    private OrderStatus status;

    private OffsetDateTime createDttm;

    private Long waiterId;

    private String tableNo;

    private Set<DishSendDto> dishes;
}
