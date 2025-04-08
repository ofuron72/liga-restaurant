package com.liga.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CompositeOrderToDishId implements Serializable {
    @Column(name = "kitchen_order_id")
    private Long orderId;

    @NotNull
    @Column(name = "dish_id")
    private Long dishId;

}
