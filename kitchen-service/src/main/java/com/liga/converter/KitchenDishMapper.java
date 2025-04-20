package com.liga.converter;

import com.liga.dto.DishDto;
import com.liga.entities.Dish;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью {@link Dish} и DTO {@link DishDto}.
 */
@Mapper
public interface KitchenDishMapper {

    DishDto toDto(Dish dish);

    Dish toEntity(DishDto dishDto);
}
