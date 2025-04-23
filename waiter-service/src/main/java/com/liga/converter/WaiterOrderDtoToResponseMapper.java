package com.liga.converter;

import com.liga.entities.WaiterOrderEntity;
import com.liga.dto.WaiterOrderResponse;
import org.mapstruct.Mapper;

/**
 * Mapper объекта {@link WaiterOrderEntity}
 * в объект {@link WaiterOrderResponse}
 */
@Mapper
public interface WaiterOrderDtoToResponseMapper {
    WaiterOrderResponse mapDtoToResponse(WaiterOrderEntity dto);
}
