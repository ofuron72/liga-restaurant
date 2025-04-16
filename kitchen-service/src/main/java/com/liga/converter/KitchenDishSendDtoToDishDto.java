package com.liga.converter;

import com.liga.dto.DishDto;
import com.liga.dto.DishSendDto;
import com.liga.entities.Dish;
import org.mapstruct.Mapper;

@Mapper
public interface KitchenDishSendDtoToDishDto {
    DishDto mapToDishDto(DishSendDto dishSendDto);

    DishSendDto mapToDishSendDto(Dish dish);
}
