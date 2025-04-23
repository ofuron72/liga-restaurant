package com.liga.repository;

import com.liga.entities.CompositeOrderToDishId;
import com.liga.entities.OrderToDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link OrderToDishEntity}.
 */
public interface KitchenOrderToDishRepository extends JpaRepository<OrderToDishEntity, CompositeOrderToDishId> {

}
