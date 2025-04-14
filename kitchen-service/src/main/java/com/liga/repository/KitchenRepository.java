package com.liga.repository;

import com.liga.entities.KitchenOrder;
import com.liga.objects.KitchenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends JpaRepository<KitchenOrder, Long> {

    @Modifying
    @Query("UPDATE KitchenOrder k SET k.status = :status WHERE k.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") KitchenStatus status);

}



