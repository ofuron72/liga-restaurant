package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderSendDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования {@link KitchenOrderDto} в {@link WaiterOrderSendDto}.
 */
@Mapper
public interface KitchenOrderDtoToWaiterOrderSendDtoMapper {

    @Mapping(source = "waiterOrderNo", target = "waiterId")
    @Mapping(source = "orderDishes", target = "dishes")
    WaiterOrderSendDto map(KitchenOrderDto waiterOrderSendDto);
}
