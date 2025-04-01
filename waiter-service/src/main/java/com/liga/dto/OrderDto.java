package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liga.objects.OrderStatus;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private Long id;

    private Integer tableNumber;

    private String description;

    private OrderStatus status;

    @JsonFormat(pattern = "MM-dd HH-mm-ss")
    private LocalDateTime orderTime;
}
