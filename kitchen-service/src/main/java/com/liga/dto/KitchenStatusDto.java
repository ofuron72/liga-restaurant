package com.liga.dto;

import com.liga.objects.KitchenStatus;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KitchenStatusDto {
    private KitchenStatus kitchenStatus;
}
