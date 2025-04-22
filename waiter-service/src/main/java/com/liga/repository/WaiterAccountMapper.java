package com.liga.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WaiterAccountMapper {

    Boolean existsById(@Param("id") Long id);
}
