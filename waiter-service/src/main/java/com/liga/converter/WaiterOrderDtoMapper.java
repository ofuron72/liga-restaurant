package com.liga.converter;

import com.liga.dto.WaiterOrderDto;
import org.mapstruct.Mapper;
import com.liga.entities.WaiterOrderEntity;

/**
 * Маппер requestDto заказа официанта в внутреннее dto заказа.
 */
@Mapper
public interface WaiterOrderDtoMapper {

    WaiterOrderDto toWaiterOrderDto(WaiterOrderEntity waiterOrderDto);

    WaiterOrderEntity toWaiterOrderEntity(WaiterOrderDto waiterOrderDto);
}
