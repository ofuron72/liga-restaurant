package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderResponse;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования {@link KitchenOrderDto} в {@link KitchenOrderResponse}.
 */
@Mapper
public interface KitchenOrderDtoToResponseMapper {

    KitchenOrderResponse mapDtoToResponse(KitchenOrderDto kitchenOrderDto);
}
