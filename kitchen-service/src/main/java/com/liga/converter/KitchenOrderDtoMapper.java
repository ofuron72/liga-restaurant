package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderReceiveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface KitchenOrderDtoMapper {
    @Mapping(target = "orderDishes", source = "dishes")
    KitchenOrderDto toDto(KitchenOrderReceiveDto kitchenOrderReceiveDto);

}
