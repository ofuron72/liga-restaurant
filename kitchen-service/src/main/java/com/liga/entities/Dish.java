package com.liga.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, представляющее блюдо.
 */
@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private Long id;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "dish_composition")
    private String dishComposition;

    @OneToMany(mappedBy = "dish")
    private Set<OrderToDish> orderDishes = new HashSet<>();
}
