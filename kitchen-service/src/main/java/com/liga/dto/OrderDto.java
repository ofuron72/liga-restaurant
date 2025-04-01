package com.liga.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liga.objects.KitchenStatus;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    private String description;

    private KitchenStatus status;

    @JsonFormat(pattern = "MM-dd HH-mm-ss")
    private LocalDateTime orderTime;
}
