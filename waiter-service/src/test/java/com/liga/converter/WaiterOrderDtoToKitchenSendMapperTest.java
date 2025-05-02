package com.liga.converter;

import com.liga.dto.CreateOrderEvent;
import com.liga.dto.DishSendDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.entities.WaiterOrderEntity;
import com.liga.objects.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WaiterOrderDtoToKitchenSendMapperTest {

    private final WaiterOrderDtoToKitchenSendDtoMapper mapper =
            Mappers.getMapper(WaiterOrderDtoToKitchenSendDtoMapper.class);

    /**
     * Проверяет, что метод map корректно преобразует WaiterOrderDto в CreateOrderEvent.
     * <p>
     * given: создается объект WaiterOrderDto с данными для заказа.
     * when: вызывается метод map для преобразования данных в CreateOrderEvent.
     * then: проверяется, что преобразованные данные соответствуют ожидаемым значениям.
     */
    @Test
    void testMap_waiterOrderDtoToCreateOrderEvent() {
        //given
        OffsetDateTime now = OffsetDateTime.now();
        DishSendDto dish1 = new DishSendDto("pizza", 1L);
        DishSendDto dish2 = new DishSendDto("pasta", 2L);

        WaiterOrderDto waiterOrderDto = new WaiterOrderDto(
                1L,
                OrderStatus.ACCEPTED,
                now,
                1L,
                "A3",
                Set.of(dish1, dish2)
        );
        //when
        CreateOrderEvent result = mapper.map(waiterOrderDto);

        //then
        assertNotNull(result);
        assertEquals(waiterOrderDto.waiterId(), result.waiterOrderNo());
        assertEquals(waiterOrderDto.id(), result.orderIdWaiterService());
        assertNotNull(result.dishes());
        assertEquals(2, result.dishes().size());
        assertTrue(result.dishes().containsAll(List.of(dish1, dish2)));
    }
}