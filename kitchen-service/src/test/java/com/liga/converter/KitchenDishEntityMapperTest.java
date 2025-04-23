package com.liga.converter;

import com.liga.dto.DishDto;
import com.liga.entities.DishEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KitchenDishEntityMapperTest {
    private final KitchenDishMapper kitchenDishMapper = new KitchenDishMapperImpl();

    /**
     * Проверяет корректность маппинга сущности
     * {@link DishEntity} в DTO {@link DishDto}.
     * <p>
     * given: Сущность блюда с заполненными полями.
     * when: Вызывается метод маппера `toDto`.
     * then: Поля DTO должны совпадать с полями исходной сущности.
     */
    @Test
    void testToDto_shouldMapCorrectly() {
        //given
        DishEntity dishEntity = DishEntity.builder()
                .id(1L)
                .balance(10L)
                .shortName("pizza")
                .dishComposition("tomato")
                .build();

        //when
        DishDto dishDto = kitchenDishMapper.toDto(dishEntity);

        //then
        assertEquals(dishEntity.getId(), dishDto.id());
        assertEquals(dishEntity.getBalance(), dishDto.balance());
        assertEquals(dishEntity.getShortName(), dishDto.shortName());
        assertEquals(dishEntity.getDishComposition(), dishDto.dishComposition());
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
                5L);

        //when
        DishEntity dishEntity = kitchenDishMapper.toEntity(dishDto);

        //then
        assertEquals(dishDto.id(), dishEntity.getId());
        assertEquals(dishDto.balance(), dishEntity.getBalance());
        assertEquals(dishDto.shortName(), dishEntity.getShortName());
        assertEquals(dishDto.dishComposition(), dishEntity.getDishComposition());
    }

}