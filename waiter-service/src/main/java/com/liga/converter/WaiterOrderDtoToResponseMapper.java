package com.liga.converter;

import com.liga.entities.WaiterOrder;
import com.liga.dto.WaiterOrderResponse;
import org.mapstruct.Mapper;

/**
 * Mapper объекта {@link WaiterOrder}
 * в объект {@link WaiterOrderResponse}
 */
@Mapper
public interface WaiterOrderDtoToResponseMapper {
    WaiterOrderResponse mapDtoToResponse(WaiterOrder dto);
}
