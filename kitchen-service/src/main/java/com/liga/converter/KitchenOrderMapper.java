package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.entities.KitchenOrder;
import org.mapstruct.Mapper;

@Mapper
public interface KitchenOrderMapper {

    KitchenOrderDto toDto(KitchenOrder kitchenOrder);

    KitchenOrder toEntity(KitchenOrderDto kitchenOrderDto);
}
