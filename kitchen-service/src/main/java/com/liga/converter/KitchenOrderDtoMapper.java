package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.CreateOrderEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface KitchenOrderDtoMapper {
    @Mapping(target = "orderDishes", source = "dishes")
    KitchenOrderDto toDto(CreateOrderEvent createOrderEvent);

}
