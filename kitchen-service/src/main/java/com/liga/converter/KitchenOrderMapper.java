package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.entities.KitchenOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface KitchenOrderMapper {

    @Mapping(target = "orderDishes", ignore = true)
    KitchenOrderDto toDto(KitchenOrder kitchenOrder);

    @Mapping(target = "orderDishes", ignore = true)
    KitchenOrder toEntity(KitchenOrderDto kitchenOrderDto);
}
