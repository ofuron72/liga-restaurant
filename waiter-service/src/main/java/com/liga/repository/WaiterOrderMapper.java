package com.liga.repository;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderStatusDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface WaiterOrderMapper {
    WaiterOrderDto getById(@Param("id") Long id);

    Set<WaiterOrderDto> getAll();

    void create(WaiterOrderDto order);

    WaiterOrderStatusDto getOrderStatus(@Param("id") Long id);

    void serveOrder(@Param("order") WaiterOrderDto order);
}
