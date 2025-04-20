package com.liga.repository;

import com.liga.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Dish}.
 */
public interface KitchenDishRepository extends JpaRepository<Dish, Long> {

    @Query("select distinct o from Dish o")
    List<Dish> findAllDistinct();

    Optional<Dish> findByShortName(String shortName);
}
