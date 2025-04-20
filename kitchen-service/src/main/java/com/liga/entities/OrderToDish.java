package com.liga.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.Builder;

/**
 * Сущность, представляющая связь между заказом и блюдами.
 * Использует составной первичный ключ {@link CompositeOrderToDishId}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Builder
@ToString
@Table(name = "order_to_dish")
public class OrderToDish {

    @EmbeddedId
    private CompositeOrderToDishId id;

    @MapsId("dishId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kitchen_order_id")
    private KitchenOrder order;

    @Column(name = "dishes_number")
    private Long dishesNumber;
}
