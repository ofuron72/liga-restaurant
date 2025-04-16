package com.liga.converter;


import com.liga.dto.WaiterOrderStatusDto;
import com.liga.dto.WaiterOrderStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface WaiterOrderStatusDtoToResponseMapper {
    WaiterOrderStatusResponse map(WaiterOrderStatusDto waiterOrderStatusDto);
}
