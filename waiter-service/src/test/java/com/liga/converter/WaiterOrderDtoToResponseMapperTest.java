package com.liga.converter;

import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderResponse;
import com.liga.objects.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WaiterOrderDtoToResponseMapperTest {

    private final WaiterOrderDtoToResponseMapper mapper = Mappers.getMapper(WaiterOrderDtoToResponseMapper.class);

    /**
     * Проверяет, что метод mapDtoToResponse корректно преобразует WaiterOrderDto в WaiterOrderResponse.
     * given: создается объект WaiterOrderDto с данными для заказа.
     * when: вызывается метод mapDtoToResponse для преобразования данных в WaiterOrderResponse.
     * then: проверяется, что преобразованные данные соответствуют ожидаемым значениям.
     */
    @Test
    void testMapDtoToResponse_shouldMapCorrectly() {
        //given
        OffsetDateTime now = OffsetDateTime.now();
        WaiterOrderDto dto = WaiterOrderDto.builder()
                .id(1L)
                .status(OrderStatus.ACCEPTED)
                .createDttm(now)
                .waiterId(123L)
                .tableNo("B5")
                .dishes(Set.of())
                .build();

        WaiterOrderResponse expectedResponse = new WaiterOrderResponse(
                1L,
                OrderStatus.ACCEPTED,
                now,
                123L,
                "B5"
        );

        //when
        WaiterOrderResponse response = mapper.mapDtoToResponse(dto);

        //then
        assertEquals(expectedResponse, response);
    }
}