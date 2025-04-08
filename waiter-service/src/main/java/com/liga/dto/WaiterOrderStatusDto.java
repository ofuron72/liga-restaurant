package com.liga.dto;

import com.liga.objects.OrderStatus;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WaiterOrderStatusDto {
    OrderStatus status;
}
