package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderResponse;
import com.liga.objects.KitchenStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class KitchenOrderDtoToResponseMapperTest {
    private final KitchenOrderDtoToResponseMapper mapper = Mappers.getMapper(KitchenOrderDtoToResponseMapper.class);

    /**
     * Проверяет корректность маппинга {@link KitchenOrderDto} в {@link KitchenOrderResponse}.
     * given: Валидный объект KitchenOrderDto.
     * when: Выполняется преобразование с помощью метода `mapDtoToResponse`.
     * then: Возвращаемый объект KitchenOrderResponse содержит те же значения, что и DTO.
     */
    @Test
    void testMapDtoToResponse_shouldMapCorrectly() {
        //given
        KitchenOrderDto dto = KitchenOrderDto.builder()
                .id(1L)
                .waiterOrderNo(1L)
                .status(KitchenStatus.ACCEPTED)
                .orderIdWaiterService(1L)
                .build();

        KitchenOrderResponse expectedOrderResponse = new KitchenOrderResponse(
                1L,
                1L,
                KitchenStatus.ACCEPTED,
                1L
        );

        //when
        KitchenOrderResponse response = mapper.mapDtoToResponse(dto);

        //then
        assertEquals(expectedOrderResponse, response);
    }
}