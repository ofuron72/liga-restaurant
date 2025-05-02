package com.liga.service;

import com.liga.converter.KitchenDishMapper;
import com.liga.dto.DishDto;
import com.liga.entities.DishEntity;
import com.liga.exceptions.DishNotFoundException;
import com.liga.repository.KitchenDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceImplTest {
    @Mock
    private KitchenDishRepository kitchenDishRepository;
    @Mock
    private KitchenDishMapper kitchenDishMapper;
    @InjectMocks
    private DishServiceImpl dishService;

    private DishEntity dish;

    private DishDto dishDto;

    @BeforeEach
    void setUp(){
        dishDto = new DishDto(1L,
                10L,
                "PIZZA",
                "tomato, cheese",
                1L
        );
        dish = DishEntity.builder()
                .id(1L)
                .balance(10L)
                .dishComposition("tomato, cheese")
                .shortName("PIZZA")
                .build();

    }

    /**
     * Проверяет поведение сервиса при запросе блюда по короткому названию, когда блюдо существует.
     * given: В репозитории существует блюдо с указанным коротким названием.
     * when: Вызывается метод getDishByShortName с коротким названием блюда.
     * then: Ожидается, что будет возвращен объект типа DishDto, соответствующий найденному блюду.
     */
    @Test
    void testGetDishByShortName_shouldReturnDto_whenDishExists() {
        //given
        String shortName = "PIZZA";

        when(kitchenDishRepository.findByShortName(shortName)).thenReturn(Optional.of(dish));
        when(kitchenDishMapper.toDto(dish)).thenReturn(dishDto);

        //when
        DishDto resultDto = dishService.getDishByShortName(shortName);

        //then
        assertNotNull(resultDto);
        assertEquals(dishDto, resultDto);
    }

    /**
     * Проверяет поведение сервиса при запросе блюда по короткому названию, когда блюдо не существует.
     * given: В репозитории отсутствует блюдо с указанным коротким названием.
     * when: Вызывается метод getDishByShortName с коротким названием блюда.
     * then: Ожидается, что будет выброшено исключение DishNotFoundException с соответствующим сообщением.
     */
    @Test
    void testGetDishByShortName_shouldThrowException_whenDishNotExists() {
        //given
        String shortName = "sushi";

        when(kitchenDishRepository.findByShortName(shortName))
                .thenReturn(Optional.empty());

        //when
        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () -> {
            dishService.getDishByShortName(shortName);
        });

        //then
        assertEquals(String.format("Dish with shortName %s not found", shortName), exception.getMessage());
    }

    /**
     * Проверяет поведение сервиса при запросе блюда по ID, когда блюдо существует.
     * given: В репозитории существует блюдо с указанным ID.
     * when: Вызывается метод getDishById с указанным ID блюда.
     * then: Ожидается, что вернется объект DishDto, соответствующий найденному блюду.
     */
    @Test
    void testGetDishById_shouldReturnDto_whenDishExists() {
        //given
        Long dishId = 1L;

        when(kitchenDishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(kitchenDishMapper.toDto(dish)).thenReturn(dishDto);

        //when
        DishDto resultDto = dishService.getDishById(dishId);

        //then
        assertNotNull(resultDto);
        assertEquals(dishDto, resultDto);
    }

    /**
     * Проверяет поведение сервиса при запросе блюда по ID, когда блюдо не существует.
     * given: В репозитории отсутствует блюдо с указанным ID.
     * when: Вызывается метод getDishById с указанным ID блюда.
     * then: Ожидается, что будет выброшено исключение DishNotFoundException с соответствующим сообщением.
     */
    @Test
    void testGetDishById_shouldThrowException_whenDishNotExists() {
        //given
        Long dishId = 1L;

        when(kitchenDishRepository.findById(dishId))
                .thenReturn(Optional.empty());

        //when
        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () -> {
            dishService.getDishById(dishId);
        });

        //then
        assertEquals(String.format("Dish with id %s not found", dishId), exception.getMessage());
    }


}