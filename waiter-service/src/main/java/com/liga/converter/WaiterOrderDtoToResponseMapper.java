package com.liga.converter;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderResponse;
import org.mapstruct.Mapper;

/**
 * Mapper объекта {@link WaiterOrderDto}
 * в объект {@link WaiterOrderResponse}
 */
@Mapper
public interface WaiterOrderDtoToResponseMapper {
    WaiterOrderResponse mapDtoToResponse(WaiterOrderDto dto);
}
