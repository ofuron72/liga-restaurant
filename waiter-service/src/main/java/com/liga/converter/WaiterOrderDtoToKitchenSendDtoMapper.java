package com.liga.converter;

import com.liga.dto.CreateOrderEvent;
import com.liga.dto.WaiterOrderDto;
import com.liga.entities.WaiterOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper {@link WaiterOrderDto} в {@link CreateOrderEvent}
 */
@Mapper
public interface WaiterOrderDtoToKitchenSendDtoMapper {

    @Mapping(source = "waiterId", target = "waiterOrderNo")
    @Mapping(source = "id", target = "orderIdWaiterService")
    CreateOrderEvent map(WaiterOrderDto waiterOrderEntity);

}
