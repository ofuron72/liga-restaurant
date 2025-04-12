package com.liga.converter;

import com.liga.dto.OrderToDishDto;
import com.liga.entities.OrderToDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface KitchenOrderToDishMapper {
    @Mapping(source = "id.orderId", target = "kitchenOrderId")
    @Mapping(source = "id.dishId", target = "dishId")
    @Mapping(source = "dishesNumber", target = "dishesNumber")
    OrderToDishDto toDto(OrderToDish entity);

    @Mapping(target = "id.orderId", source = "kitchenOrderId")
    @Mapping(target = "id.dishId", source = "dishId")
    @Mapping(target = "dishesNumber", source = "dishesNumber")
    OrderToDish toEntity(OrderToDishDto dto);
}
