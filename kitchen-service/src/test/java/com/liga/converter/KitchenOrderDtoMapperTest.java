package com.liga.converter;

import com.liga.dto.CreateOrderEvent;
import com.liga.dto.DishDto;
import com.liga.dto.DishSendDto;
import com.liga.dto.KitchenOrderDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

class KitchenOrderDtoMapperTest {
    private final KitchenOrderDtoMapper mapper = Mappers.getMapper(KitchenOrderDtoMapper.class);

    /**
     * Проверяет маппинг события {@link CreateOrderEvent} в DTO {@link KitchenOrderDto}.
     * given: Событие создания заказа с двумя блюдами.
     * when: Выполняется преобразование с помощью метода `toDto`.
     * then: Возвращаемый DTO должен иметь те же значения полей.
     */
    @Test
    void testToDto_shouldMapCreateOrderEventToKitchenOrderDto() {
        //given
        DishSendDto dish1 = new DishSendDto("pizza", 2L);
        DishSendDto dish2 = new DishSendDto("pasta", 1L);

        CreateOrderEvent event = new CreateOrderEvent(1L, 1L, List.of(dish1, dish2));

        DishDto expectedDishDto1 = new DishDto(null,
                null,
                "pizza",
                null,
                2L);

        DishDto expectedDishDto2 = new DishDto(null,
                null,
                "pasta",
                null,
                1L);

        KitchenOrderDto expectedDto = KitchenOrderDto.builder()
                .waiterOrderNo(1L)
                .orderIdWaiterService(1L)
                .orderDishes(Set.of(expectedDishDto1, expectedDishDto2))
                .build();

        //when
        KitchenOrderDto result = mapper.toDto(event);

        //then
        assertEquals(expectedDto, result);
    }

}