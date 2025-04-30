package com.liga.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.io.Serializable;

/**
 * Составной идентификатор для связи между заказом и блюдом.
 * Используется в качестве первичного ключа в базе данных для таблицы,
 * которая связывает заказы и блюда.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CompositeOrderToDishId implements Serializable {
    @Column(name = "kitchen_order_id")
    private Long orderId;

    @NotNull
    @Column(name = "dish_id")
    private Long dishId;

}
