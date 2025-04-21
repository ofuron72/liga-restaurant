package com.liga.repository;

import com.liga.entities.WaiterMenuItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface WaiterMenuMapper {
    Set<WaiterMenuItem> getAll();
}
