package com.liga.repository;

import com.liga.entities.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link DishEntity}.
 */
public interface KitchenDishRepository extends JpaRepository<DishEntity, Long> {

    @Query("select distinct o from DishEntity o")
    List<DishEntity> findAllDistinct();

    Optional<DishEntity> findByShortName(String shortName);
}
