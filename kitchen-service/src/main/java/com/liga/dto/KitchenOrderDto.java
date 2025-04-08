package com.liga.dto;

import com.liga.objects.KitchenStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KitchenOrderDto {

    private Long id;

    @NotNull(message = "waiter order number must not be null")
    private Long waiterOrderNo;

    private KitchenStatus status;
}
