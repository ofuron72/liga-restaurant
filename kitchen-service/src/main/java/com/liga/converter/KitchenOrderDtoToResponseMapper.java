package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderResponse;
import org.mapstruct.Mapper;

@Mapper
public interface KitchenOrderDtoToResponseMapper {
    KitchenOrderResponse mapDtoToResponse(KitchenOrderDto kitchenOrderDto);
}
