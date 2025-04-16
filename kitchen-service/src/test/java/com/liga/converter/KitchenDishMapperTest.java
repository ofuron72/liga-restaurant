package com.liga.converter;

import com.liga.dto.DishDto;
import com.liga.entities.Dish;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KitchenDishMapperTest {
    private final KitchenDishMapper kitchenDishMapper = new KitchenDishMapperImpl();

    @Test
    void testToDto() {
        Dish dish = Dish.builder()
                .id(1L)
                .balance(10L)
                .shortName("pizza")
                .dishComposition("tomato")
                .build();

        DishDto dishDto = kitchenDishMapper.toDto(dish);

        assertEquals(dish.getId(), dishDto.id());
        assertEquals(dish.getBalance(), dishDto.balance());
        assertEquals(dish.getShortName(), dishDto.shortName());
        assertEquals(dish.getDishComposition(), dishDto.dishComposition());
    }

    @Test
    void testToEntity() {
        DishDto dishDto = new DishDto(1L,
                10L,
                "pizza",
                "tomato",
                5L);

        Dish dish = kitchenDishMapper.toEntity(dishDto);

        assertEquals(dishDto.id(), dish.getId());
        assertEquals(dishDto.balance(), dish.getBalance());
        assertEquals(dishDto.shortName(), dish.getShortName());
        assertEquals(dishDto.dishComposition(), dish.getDishComposition());
    }

}