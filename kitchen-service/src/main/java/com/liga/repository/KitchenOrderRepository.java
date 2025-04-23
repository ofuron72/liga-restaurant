package com.liga.repository;

import com.liga.entities.KitchenOrderEntity;
import com.liga.objects.KitchenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link KitchenOrderEntity}.
 */
public interface KitchenOrderRepository extends JpaRepository<KitchenOrderEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE KitchenOrderEntity k SET k.status = :status WHERE k.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") KitchenStatus status);

    @Query("select distinct o from KitchenOrderEntity o")
    List<KitchenOrderEntity> findAllDistinct();
}



