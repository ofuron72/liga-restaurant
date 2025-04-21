package com.liga.converter;

import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.entities.WaiterOrder;
import org.mapstruct.Mapper;

/**
 * Маппер requestDto заказа официанта в внутреннее dto заказа.
 */
@Mapper
public interface WaiterOrderDtoMapper {

    WaiterOrder toWaiterOrderDto(WaiterOrderCreateRequestDto waiterOrderDto);
}
