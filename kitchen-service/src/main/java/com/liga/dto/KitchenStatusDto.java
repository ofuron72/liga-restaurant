package com.liga.dto;

import com.liga.objects.KitchenStatus;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * DTO, представляющий статус заказа на кухне.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class KitchenStatusDto {
    private KitchenStatus kitchenStatus;
}
