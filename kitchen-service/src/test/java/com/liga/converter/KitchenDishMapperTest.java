package com.liga.converter;

import com.liga.dto.DishDto;
import com.liga.entities.Dish;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KitchenDishMapperTest {
    private final KitchenDishMapper kitchenDishMapper = new KitchenDishMapperImpl();

    /**
     * Проверяет корректность маппинга сущности
     * {@link Dish} в DTO {@link DishDto}.
     * <p>
     * given: Сущность блюда с заполненными полями.
     * when: Вызывается метод маппера `toDto`.
     * then: Поля DTO должны совпадать с полями исходной сущности.
     */
    @Test
    void testToDto_shouldMapCorrectly() {
        //given
        Dish dish = Dish.builder()
                .id(1L)
                .balance(10L)
                .shortName("pizza")
                .dishComposition("tomato")
                .build();

        //when
        DishDto dishDto = kitchenDishMapper.toDto(dish);

        //then
        assertEquals(dish.getId(), dishDto.id());
        assertEquals(dish.getBalance(), dishDto.balance());
        assertEquals(dish.getShortName(), dishDto.shortName());
        assertEquals(dish.getDishComposition(), dishDto.dishComposition());
    }

    /**
     * Проверяет корректность маппинга DTO {@link DishDto} в сущность {@link Dish}.
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
        Dish dish = kitchenDishMapper.toEntity(dishDto);

        //then
        assertEquals(dishDto.id(), dish.getId());
        assertEquals(dishDto.balance(), dish.getBalance());
        assertEquals(dishDto.shortName(), dish.getShortName());
        assertEquals(dishDto.dishComposition(), dish.getDishComposition());
    }

}