package com.liga.converter;

import com.liga.dto.KitchenOrderDto;
import com.liga.entities.KitchenOrder;
import com.liga.objects.KitchenStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class KitchenOrderMapperTest {
    private final KitchenOrderMapper mapper = Mappers.getMapper(KitchenOrderMapper.class);

    /**
     * Проверяет корректность маппинга из {@link KitchenOrder} в {@link KitchenOrderDto}.
     * <p>
     * given: Сущность KitchenOrder с установленными полями.
     * when: Выполняется преобразование через метод `toDto`.
     * then: Полученный KitchenOrderDto содержит те же значения полей, что и исходная сущность.
     */
    @Test
    void testToDto_shouldMapFieldsCorrectly() {
        //given
        KitchenOrder order = KitchenOrder.builder()
                .id(1L)
                .waiterOrderNo(1L)
                .status(KitchenStatus.ACCEPTED)
                .createDttm(ZonedDateTime.now())
                .orderIdWaiterService(1L)
                .build();

        //when
        KitchenOrderDto dto = mapper.toDto(order);

        //then
        assertNotNull(dto);
        assertEquals(order.getId(), dto.getId());
        assertEquals(order.getWaiterOrderNo(), dto.getWaiterOrderNo());
        assertEquals(order.getStatus(), dto.getStatus());
        assertEquals(order.getOrderIdWaiterService(), dto.getOrderIdWaiterService());
    }

    /**
     * Проверяет корректность маппинга из {@link KitchenOrderDto} в {@link KitchenOrder}.
     * <p>
     * given: DTO объект KitchenOrderDto с заполненными полями.
     * when: Выполняется преобразование через метод `toEntity`.
     * then: Полученная сущность KitchenOrder содержит те же значения полей, что и исходный DTO.
     */
    @Test
    void testToEntity_shouldMapFieldsCorrectly() {
        //given
        KitchenOrderDto dto = KitchenOrderDto.builder()
                .id(2L)
                .waiterOrderNo(1L)
                .status(KitchenStatus.REJECTED)
                .orderIdWaiterService(1L)
                .build();

        //when
        KitchenOrder entity = mapper.toEntity(dto);

        //then
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getWaiterOrderNo(), entity.getWaiterOrderNo());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getOrderIdWaiterService(), entity.getOrderIdWaiterService());
    }


}