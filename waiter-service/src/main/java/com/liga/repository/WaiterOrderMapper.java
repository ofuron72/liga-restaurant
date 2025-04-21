package com.liga.repository;

import com.liga.entities.WaiterOrder;
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
    WaiterOrder getById(@Param("id") Long id);

    Set<WaiterOrder> getAll();

    void create(WaiterOrder order);

    WaiterOrderStatusDto getOrderStatus(@Param("id") Long id);

    void updateStatusOrder(@Param("order") WaiterOrder order);

}
