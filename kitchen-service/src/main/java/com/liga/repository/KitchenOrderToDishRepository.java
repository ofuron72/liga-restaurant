package com.liga.repository;

import com.liga.entities.CompositeOrderToDishId;
import com.liga.entities.OrderToDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenOrderToDishRepository extends JpaRepository<OrderToDish, CompositeOrderToDishId> {

}
