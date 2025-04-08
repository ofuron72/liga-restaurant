package com.liga.dto;

import com.liga.objects.OrderStatus;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WaiterOrderDto {
    private Long id;

    private OrderStatus status;

    private OffsetDateTime createDttm;

    @NotNull(message = "waiterId must not be null")
    private Long waiterId;

    @NotNull(message = "tableNo must not be null")
    private String tableNo;
}
