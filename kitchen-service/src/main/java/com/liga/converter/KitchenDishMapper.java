package com.liga.converter;

import com.liga.dto.DishDto;
import com.liga.entities.Dish;
import org.mapstruct.Mapper;

@Mapper
public interface KitchenDishMapper {
    DishDto toDto(Dish dish);

    Dish toEntity(DishDto dishDto);
}
