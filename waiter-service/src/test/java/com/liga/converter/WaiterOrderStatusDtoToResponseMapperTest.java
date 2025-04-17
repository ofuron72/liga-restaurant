package com.liga.converter;

import com.liga.dto.WaiterOrderStatusDto;
import com.liga.dto.WaiterOrderStatusResponse;
import com.liga.objects.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class WaiterOrderStatusDtoToResponseMapperTest {

    private final WaiterOrderStatusDtoToResponseMapper mapper =
            Mappers.getMapper(WaiterOrderStatusDtoToResponseMapper.class);

    /**
     * Проверяет, что метод map корректно преобразует WaiterOrderStatusDto в WaiterOrderStatusResponse.
     * <p>
     * given: создается объект WaiterOrderStatusDto с состоянием заказа.
     * when: вызывается метод map для преобразования данных в WaiterOrderStatusResponse.
     * then: проверяется, что преобразованные данные соответствуют ожидаемым значениям.
     */
    @Test
    void testMap_shouldMapCorrectly() {
        //given
        WaiterOrderStatusDto dto = new WaiterOrderStatusDto(OrderStatus.REJECTED_BY_THE_KITCHEN);

        //when
        WaiterOrderStatusResponse response = mapper.map(dto);

        //then
        assertNotNull(response);
        assertEquals(OrderStatus.REJECTED_BY_THE_KITCHEN, response.status());
    }
}