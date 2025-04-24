package com.liga.dto;

import com.liga.objects.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaiterOrderStatusDto {
    OrderStatus status;
}
