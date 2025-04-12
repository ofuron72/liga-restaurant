package com.liga.converter;

import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.dto.WaiterOrderDto;
import org.mapstruct.Mapper;

@Mapper
public interface WaiterOrderDtoMapper {
    WaiterOrderCreateRequestDto toWaiterOrderCreateRequestDto(WaiterOrderDto waiterOrderDto);

    WaiterOrderDto toWaiterOrderDto(WaiterOrderCreateRequestDto waiterOrderDto);

}
