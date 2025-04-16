package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderSendDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface KitchenOrderDtoToWaiterOrderSendDtoMapper {

    @Mapping(source = "waiterOrderNo", target = "waiterId")
    @Mapping(source = "orderDishes", target = "dishes")
    WaiterOrderSendDto map(KitchenOrderDto waiterOrderSendDto);
}
