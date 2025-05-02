package com.liga.converter;

import com.liga.dto.OrderToDishDto;
import com.liga.entities.CompositeOrderToDishId;

import com.liga.entities.OrderToDishEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class KitchenOrderToDishMapperTest {
    private final KitchenOrderToDishMapper mapper = Mappers.getMapper(KitchenOrderToDishMapper.class);

    /**
     * Проверяет корректность маппинга из {@link OrderToDishEntity} в {@link OrderToDishDto}.
     * given: Сущность OrderToDish с составным ID и количеством блюд.
     * when: Выполняется преобразование в DTO через метод `toDto`.
     * then: Все поля DTO соответствуют значениям из сущности.
     */
    @Test
    void testToDto_shouldMapCorrectly() {
        //given
        CompositeOrderToDishId id = new CompositeOrderToDishId(1L, 10L);
        OrderToDishEntity entity = OrderToDishEntity.builder()
                .id(id)
                .dishesNumber(5L)
                .build();

        OrderToDishDto expectedDto = new OrderToDishDto(1L,
                10L,
                5L
        );

        //when
        OrderToDishDto dto = mapper.toDto(entity);

        //then
        assertEquals(expectedDto, dto);
    }

    /**
     * Проверяет корректность маппинга из {@link OrderToDishDto} в {@link OrderToDishEntity}.
     * given: DTO с kitchenOrderId, dishId и количеством блюд.
     * when: DTO маппится в сущность OrderToDish.
     * then: Все поля сущности соответствуют значениям из DTO.
     */
    @Test
    void testToEntity_shouldMapCorrectly() {
        //given
        OrderToDishDto dto = new OrderToDishDto(2L, 2L, 3L);

        CompositeOrderToDishId expectedId = new CompositeOrderToDishId(2L, 2L);

        OrderToDishEntity expectedEntity = OrderToDishEntity.builder()
                .id(expectedId)
                .dishesNumber(3L)
                .build();

        //when
        OrderToDishEntity entity = mapper.toEntity(dto);

        //then
        assertEquals(expectedEntity, entity);
    }

}