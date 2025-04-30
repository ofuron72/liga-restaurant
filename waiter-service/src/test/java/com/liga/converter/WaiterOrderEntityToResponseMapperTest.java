package com.liga.converter;

import com.liga.entities.WaiterOrderEntity;
import com.liga.dto.WaiterOrderResponse;
import com.liga.objects.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WaiterOrderEntityToResponseMapperTest {

    private final WaiterOrderDtoToResponseMapper mapper = Mappers.getMapper(WaiterOrderDtoToResponseMapper.class);

    /**
     * Проверяет, что метод mapDtoToResponse корректно преобразует WaiterOrderDto в WaiterOrderResponse.
     * <p>
     * given: создается объект WaiterOrderDto с данными для заказа.
     * when: вызывается метод mapDtoToResponse для преобразования данных в WaiterOrderResponse.
     * then: проверяется, что преобразованные данные соответствуют ожидаемым значениям.
     */
    @Test
    void testMapDtoToResponse_shouldMapCorrectly() {

        //given
        WaiterOrderEntity dto = WaiterOrderEntity.builder()
                .id(1L)
                .status(OrderStatus.ACCEPTED)
                .createDttm(OffsetDateTime.now())
                .waiterId(123L)
                .tableNo("B5")
                .dishes(Set.of())
                .build();

        //when
        WaiterOrderResponse response = mapper.mapDtoToResponse(dto);

        //then
        assertNotNull(response);
        assertEquals(dto.getId(), response.id());
        assertEquals(dto.getStatus(), response.status());
        assertEquals(dto.getCreateDttm(), response.createDttm());
        assertEquals(dto.getWaiterId(), response.waiterId());
        assertEquals(dto.getTableNo(), response.tableNo());
    }
}