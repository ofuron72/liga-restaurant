package com.liga.repository;

import com.liga.entities.KitchenOrder;
import com.liga.objects.KitchenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link KitchenOrder}.
 */
public interface KitchenOrderRepository extends JpaRepository<KitchenOrder, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE KitchenOrder k SET k.status = :status WHERE k.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") KitchenStatus status);

    @Query("select distinct o from KitchenOrder o")
    List<KitchenOrder> findAllDistinct();
}



