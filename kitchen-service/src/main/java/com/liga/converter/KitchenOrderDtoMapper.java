package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderReceiveDto;
import org.mapstruct.Mapper;

@Mapper
public interface KitchenOrderDtoMapper {
    KitchenOrderDto toDto(KitchenOrderReceiveDto kitchenOrderReceiveDto);

    KitchenOrderReceiveDto toReceiveDto(KitchenOrderDto kitchenOrderDto);
}
