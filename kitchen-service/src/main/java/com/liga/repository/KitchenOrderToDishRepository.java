package com.liga.repository;

import com.liga.entities.CompositeOrderToDishId;
import com.liga.entities.OrderToDish;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link OrderToDish}.
 */
public interface KitchenOrderToDishRepository extends JpaRepository<OrderToDish, CompositeOrderToDishId> {

}
