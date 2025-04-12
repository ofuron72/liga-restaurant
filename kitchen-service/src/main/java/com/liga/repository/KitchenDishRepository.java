package com.liga.repository;

import com.liga.entities.Dish;
import com.liga.entities.KitchenOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface KitchenDishRepository extends JpaRepository<Dish, Long> {

    @Query("select distinct o from Dish o")
    Set<Dish> findAllDistinct();

    Optional<Dish> findByShortName(String shortName);
}
