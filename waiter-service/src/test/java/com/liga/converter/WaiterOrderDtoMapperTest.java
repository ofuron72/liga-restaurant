package com.liga.converter;

import com.liga.dto.DishSendDto;
import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.entities.WaiterOrderEntity;
import com.liga.objects.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WaiterOrderDtoMapperTest {
    private final WaiterOrderDtoMapper waiterOrderDtoMapper = Mappers.getMapper(WaiterOrderDtoMapper.class);

    /**
     * Проверяет, что метод toWaiterOrderDto корректно преобразует WaiterOrderCreateRequestDto в WaiterOrderDto.
     * given: создается объект WaiterOrderCreateRequestDto с данными для заказа.
     * when: вызывается метод toWaiterOrderDto для преобразования данных в WaiterOrderDto.
     * then: проверяется, что преобразованные данные соответствуют ожидаемым значениям.
     */
    @Test
    void testToWaiterOrderDto() {
        //given
        OffsetDateTime now = OffsetDateTime.now();
        DishSendDto dish1 = new DishSendDto("pizza", 2L);
        DishSendDto dish2 = new DishSendDto("pasta", 1L);

        WaiterOrderDto dto = new WaiterOrderDto(
                123L,
                OrderStatus.ACCEPTED,
                now,
                123L,
                "A1",
                Set.of(dish1, dish2)
        );

        WaiterOrderEntity expectedEntity = new WaiterOrderEntity(
                123L,
                OrderStatus.ACCEPTED,
                now,
                123L,
                "A1",
                Set.of(dish1, dish2)
        );

        //when
        WaiterOrderEntity result = waiterOrderDtoMapper.toWaiterOrderEntity(dto);

        //then
        assertEquals(result, expectedEntity);
    }

}