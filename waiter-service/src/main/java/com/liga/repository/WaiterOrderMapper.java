package com.liga.repository;

import com.liga.entities.WaiterOrderEntity;
import com.liga.dto.WaiterOrderStatusDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Интерфейс для работы с заказами через MyBatis.
 */
@Mapper
@Repository
public interface WaiterOrderMapper {
    WaiterOrderEntity getById(@Param("id") Long id);

    Set<WaiterOrderEntity> getAll();

    void create(WaiterOrderEntity order);

    WaiterOrderStatusDto getOrderStatus(@Param("id") Long id);

    void updateStatusOrder(@Param("order") WaiterOrderEntity order);

}
