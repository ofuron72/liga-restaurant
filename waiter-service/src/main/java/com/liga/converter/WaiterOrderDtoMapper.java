package com.liga.converter;

import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.entities.WaiterOrderEntity;
import org.mapstruct.Mapper;

/**
 * Маппер requestDto заказа официанта в внутреннее dto заказа.
 */
@Mapper
public interface WaiterOrderDtoMapper {

    WaiterOrderEntity toWaiterOrderDto(WaiterOrderCreateRequestDto waiterOrderDto);
}
