package com.liga.converter;

import com.liga.dto.CreateOrderEvent;
import com.liga.entities.WaiterOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper {@link WaiterOrderEntity} в {@link CreateOrderEvent}
 */
@Mapper
public interface WaiterOrderDtoToKitchenSendDtoMapper {

    @Mapping(source = "waiterId", target = "waiterOrderNo")
    @Mapping(source = "id", target = "orderIdWaiterService")
    CreateOrderEvent map(WaiterOrderEntity waiterOrderEntity);

}
