package com.liga.converter;

import com.liga.dto.OrderToDishDto;
import com.liga.entities.CompositeOrderToDishId;
import com.liga.entities.OrderToDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Маппер для преобразования между сущностью {@link OrderToDish} и DTO {@link OrderToDishDto}.
 * Также содержит метод для маппинга составного идентификатора {@link CompositeOrderToDishId}.
 */
@Mapper
public interface KitchenOrderToDishMapper {

    @Mapping(source = "id.orderId", target = "kitchenOrderId")
    @Mapping(source = "id.dishId", target = "dishId")
    OrderToDishDto toDto(OrderToDish entity);

    @Mapping(target = "id", source = "dto", qualifiedByName = "mapToCompositeId")
    OrderToDish toEntity(OrderToDishDto dto);

    /**
     * Метод для преобразования DTO в составной идентификатор.
     */
    @Named("mapToCompositeId")
    default CompositeOrderToDishId mapToCompositeId(OrderToDishDto dto) {
        return new CompositeOrderToDishId(dto.kitchenOrderId(), dto.dishId());
    }

}
