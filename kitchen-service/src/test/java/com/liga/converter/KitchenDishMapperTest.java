package com.liga.converter;

import com.liga.dto.DishDto;

import com.liga.entities.DishEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class KitchenDishMapperTest {
    private final KitchenDishMapper kitchenDishMapper = Mappers.getMapper(KitchenDishMapper.class);

    /**
     * Проверяет корректность маппинга сущности
     * {@link DishEntity} в DTO {@link DishDto}.
     * given: Сущность блюда с заполненными полями.
     * when: Вызывается метод маппера `toDto`.
     * then: Поля DTO должны совпадать с полями исходной сущности.
     */
    @Test
    void testToDto_shouldMapCorrectly() {
        //given
        DishEntity dish = DishEntity.builder()
                .id(1L)
                .balance(10L)
                .shortName("pizza")
                .dishComposition("tomato")
                .build();

        DishDto expectedDto = new DishDto(1L,
                10L,
                "pizza",
                "tomato",
                null);

        //when
        DishDto dishDto = kitchenDishMapper.toDto(dish);

        //then
        assertEquals(expectedDto, dishDto);

    }

    /**
     * Проверяет корректность маппинга DTO {@link DishDto} в сущность {@link DishEntity}.
     * <p>
     * given: DTO блюда с заданными значениями полей.
     * when: Вызывается метод маппера `toEntity`.
     * then: Полученная сущность должна иметь те же значения полей, что и DTO.
     */
    @Test
    void testToEntity_shouldMapCorrectly() {
        //given
        DishDto dishDto = new DishDto(1L,
                10L,
                "pizza",
                "tomato",
                1L);

        DishEntity expectedDish = DishEntity.builder()
                .id(1L)
                .balance(10L)
                .shortName("pizza")
                .dishComposition("tomato")
                .build();

        //when
        DishEntity dish = kitchenDishMapper.toEntity(dishDto);

        //then
        assertEquals(dish, expectedDish);
    }

}