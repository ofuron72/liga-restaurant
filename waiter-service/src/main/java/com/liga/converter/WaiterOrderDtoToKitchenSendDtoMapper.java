package com.liga.converter;

import com.liga.dto.KitchenOrderSendDto;
import com.liga.dto.WaiterOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WaiterOrderDtoToKitchenSendDtoMapper {

    @Mapping(source = "waiterId", target = "waiterOrderNo")
    @Mapping(source = "id", target = "orderIdWaiterService")
    KitchenOrderSendDto map(WaiterOrderDto waiterOrderDto);
}
