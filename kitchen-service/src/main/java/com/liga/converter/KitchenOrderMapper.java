package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.entities.KitchenOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностью {@link KitchenOrderEntity} и DTO {@link KitchenOrderDto}.
 */
@Mapper
public interface KitchenOrderMapper {

    @Mapping(target = "orderDishes", ignore = true)
    KitchenOrderDto toDto(KitchenOrderEntity kitchenOrderEntity);

    @Mapping(target = "orderDishes", ignore = true)
    KitchenOrderEntity toEntity(KitchenOrderDto kitchenOrderDto);
}
