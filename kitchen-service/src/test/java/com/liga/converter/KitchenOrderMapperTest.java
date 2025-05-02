package com.liga.converter;

import com.liga.dto.KitchenOrderDto;

import com.liga.entities.KitchenOrderEntity;
import com.liga.objects.KitchenStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class KitchenOrderMapperTest {
    private final KitchenOrderMapper mapper = Mappers.getMapper(KitchenOrderMapper.class);

    /**
     * Проверяет корректность маппинга из {@link KitchenOrderEntity} в {@link KitchenOrderDto}.
     * given: Сущность KitchenOrder с установленными полями.
     * when: Выполняется преобразование через метод `toDto`.
     * then: Полученный KitchenOrderDto содержит те же значения полей, что и исходная сущность.
     */
    @Test
    void testToDto_shouldMapFieldsCorrectly() {
        //given
        KitchenOrderEntity order = KitchenOrderEntity.builder()
                .id(1L)
                .waiterOrderNo(1L)
                .status(KitchenStatus.ACCEPTED)
                .createDttm(ZonedDateTime.now())
                .orderIdWaiterService(1L)
                .build();

        KitchenOrderDto expectedDto = KitchenOrderDto.builder()
                .id(1L)
                .waiterOrderNo(1L)
                .status(KitchenStatus.ACCEPTED)
                .orderIdWaiterService(1L)
                .build();

        //when
        KitchenOrderDto dto = mapper.toDto(order);

        //then
        assertEquals(expectedDto, dto);
    }

    /**
     * Проверяет корректность маппинга из {@link KitchenOrderDto} в {@link KitchenOrderEntity}.
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

        KitchenOrderEntity expectedEntity = KitchenOrderEntity.builder()
                .id(2L)
                .waiterOrderNo(1L)
                .status(KitchenStatus.REJECTED)
                .orderIdWaiterService(1L)
                .build();

        //when
        KitchenOrderEntity entity = mapper.toEntity(dto);

        //then
        assertEquals(expectedEntity, entity);
    }


}