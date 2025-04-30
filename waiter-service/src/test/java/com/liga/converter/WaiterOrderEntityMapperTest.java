package com.liga.converter;

import com.liga.dto.DishSendDto;
import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.entities.WaiterOrderEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WaiterOrderEntityMapperTest {
    private final WaiterOrderDtoMapper waiterOrderDtoMapper = Mappers.getMapper(WaiterOrderDtoMapper.class);

    /**
     * Проверяет, что метод toWaiterOrderDto корректно преобразует WaiterOrderCreateRequestDto в WaiterOrderDto.
     * <p>
     * given: создается объект WaiterOrderCreateRequestDto с данными для заказа.
     * when: вызывается метод toWaiterOrderDto для преобразования данных в WaiterOrderDto.
     * then: проверяется, что преобразованные данные соответствуют ожидаемым значениям.
     */
    @Test
    void testToWaiterOrderDto() {
        //given
        DishSendDto dish1 = new DishSendDto("pizza", 2L);
        DishSendDto dish2 = new DishSendDto("pasta", 1L);

        WaiterOrderCreateRequestDto waiterOrderCreateRequestDto = new WaiterOrderCreateRequestDto(
                123L,
                "A1",
                List.of(dish1, dish2) // dishes
        );

        //when
        WaiterOrderEntity waiterOrderEntity = waiterOrderDtoMapper.toWaiterOrderDto(waiterOrderCreateRequestDto);

        //then
        assertNotNull(waiterOrderEntity);
        assertEquals(waiterOrderCreateRequestDto.waiterId(), waiterOrderEntity.getWaiterId());
        assertEquals(waiterOrderCreateRequestDto.tableNo(), waiterOrderEntity.getTableNo());
        assertEquals(waiterOrderCreateRequestDto.dishes().size(), waiterOrderEntity.getDishes().size());
        assertTrue(waiterOrderEntity.getDishes().containsAll(Set.of(dish1, dish2)));
    }

}