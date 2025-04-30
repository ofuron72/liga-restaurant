package com.liga.converter;

import com.liga.dto.DishDto;
import com.liga.entities.DishEntity;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью {@link DishEntity} и DTO {@link DishDto}.
 */
@Mapper
public interface KitchenDishMapper {

    DishDto toDto(DishEntity dishEntity);

    DishEntity toEntity(DishDto dishDto);
}
