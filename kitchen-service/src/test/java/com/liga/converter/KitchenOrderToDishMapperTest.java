package com.liga.converter;

import com.liga.dto.OrderToDishDto;
import com.liga.entities.CompositeOrderToDishId;
import com.liga.entities.OrderToDish;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class KitchenOrderToDishMapperTest {
    private final KitchenOrderToDishMapper mapper = Mappers.getMapper(KitchenOrderToDishMapper.class);

    /**
     * Проверяет корректность маппинга из {@link OrderToDish} в {@link OrderToDishDto}.
     * <p>
     * given: Сущность OrderToDish с составным ID и количеством блюд.
     * when: Выполняется преобразование в DTO через метод `toDto`.
     * then: Все поля DTO соответствуют значениям из сущности.
     */
    @Test
    void testToDto_shouldMapCorrectly() {
        //given
        CompositeOrderToDishId id = new CompositeOrderToDishId(1L, 10L);
        OrderToDish entity = OrderToDish.builder()
                .id(id)
                .dishesNumber(5L)
                .build();

        //when
        OrderToDishDto dto = mapper.toDto(entity);

        //then
        assertEquals(1L, dto.kitchenOrderId());
        assertEquals(10L, dto.dishId());
        assertEquals(5L, dto.dishesNumber());
    }

    /**
     * Проверяет корректность маппинга из {@link OrderToDishDto} в {@link OrderToDish}.
     * <p>
     * given: DTO с kitchenOrderId, dishId и количеством блюд.
     * when: DTO маппится в сущность OrderToDish.
     * then: Все поля сущности соответствуют значениям из DTO.
     */
    @Test
    void testToEntity_shouldMapCorrectly() {
        //given
        OrderToDishDto dto = new OrderToDishDto(2L, 2L, 3L);

        //when
        OrderToDish entity = mapper.toEntity(dto);

        //then
        assertNotNull(entity.getId());
        assertEquals(2L, entity.getId().getOrderId());
        assertEquals(2L, entity.getId().getDishId());
        assertEquals(3L, entity.getDishesNumber());
    }

}