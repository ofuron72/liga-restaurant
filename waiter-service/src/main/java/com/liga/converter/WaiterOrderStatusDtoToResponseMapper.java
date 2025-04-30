package com.liga.converter;


import com.liga.dto.WaiterOrderStatusDto;
import com.liga.dto.WaiterOrderStatusResponse;
import org.mapstruct.Mapper;

/**
 * Mapper для преобразования объекта {@link WaiterOrderStatusDto}
 * в объект {@link WaiterOrderStatusResponse}
 */
@Mapper
public interface WaiterOrderStatusDtoToResponseMapper {
    WaiterOrderStatusResponse map(WaiterOrderStatusDto waiterOrderStatusDto);
}
