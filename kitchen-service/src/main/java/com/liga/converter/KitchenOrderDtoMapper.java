package com.liga.converter;

import com.liga.dto.CreateOrderEvent;
import com.liga.dto.KitchenOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования{@link CreateOrderEvent} в DTO {@link KitchenOrderDto}.
 */
@Mapper
public interface KitchenOrderDtoMapper {

    @Mapping(target = "orderDishes", source = "dishes")
    KitchenOrderDto toDto(CreateOrderEvent createOrderEvent);
}
