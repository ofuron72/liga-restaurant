package com.liga.converter;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderResponse;
import org.mapstruct.Mapper;

@Mapper
public interface WaiterOrderDtoToResponseMapper {
    WaiterOrderResponse mapDtoToResponse(WaiterOrderDto dto);
}
