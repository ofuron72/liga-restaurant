package com.liga.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @JsonIgnore
    private List<OrderToDish> orderDishes = new ArrayList<>();
}
