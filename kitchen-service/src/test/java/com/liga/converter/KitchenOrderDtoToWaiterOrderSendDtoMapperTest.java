package com.liga.converter;

import com.liga.dto.DishDto;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderSendDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class KitchenOrderDtoToWaiterOrderSendDtoMapperTest {

    private final KitchenOrderDtoToWaiterOrderSendDtoMapper mapper =
            Mappers.getMapper(KitchenOrderDtoToWaiterOrderSendDtoMapper.class);

    /**
     * Проверяет корректность маппинга {@link KitchenOrderDto} в {@link WaiterOrderSendDto}.
     * given: Валидный KitchenOrderDto с двумя блюдами.
     * when: Выполняется маппинг с помощью метода `map`.
     * then: Возвращаемый WaiterOrderSendDto содержит корректные данные, включая список всех блюд.
     */
    @Test
    void testMap_shouldMapCorrectly() {
        //given
        DishDto dish1 = new DishDto(1L, 10L, "pizza", "cheese", 1L);
        DishDto dish2 = new DishDto(2L, 5L, "pasta", "tomato", 2L);

        KitchenOrderDto kitchenOrderDto = KitchenOrderDto.builder()
                .id(1L)
                .waiterOrderNo(1L)
                .orderIdWaiterService(1L)
                .orderDishes(Set.of(dish1, dish2))
                .build();

        //when
        WaiterOrderSendDto result = mapper.map(kitchenOrderDto);

        //then
        assertNotNull(result);
        assertEquals(kitchenOrderDto.getWaiterOrderNo(), result.waiterId());
        assertEquals(kitchenOrderDto.getOrderIdWaiterService(), result.orderIdWaiterService());
        assertNotNull(result.dishes());
        assertEquals(2, result.dishes().size());
        assertTrue(result.dishes().containsAll(List.of(dish1, dish2)));
    }
}