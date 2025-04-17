package com.liga.service.orchestrator;

import com.liga.converter.KitchenOrderDtoToWaiterOrderSendDtoMapper;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.WaiterOrderSendDto;
import com.liga.exceptions.SendOrderFeignException;
import com.liga.feign.WaiterFeignClient;
import com.liga.service.KitchenService;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KitchenOrderOrchestratorTest {

    KitchenOrderDto dto;
    WaiterOrderSendDto sendDto;
    @Mock
    private WaiterFeignClient waiterFeignClient;
    @Mock
    private KitchenService kitchenService;
    @Mock
    private KitchenOrderDtoToWaiterOrderSendDtoMapper kitchenOrderDtoToWaiterOrderSendDtoMapper;
    @InjectMocks
    private KitchenOrderOrchestrator kitchenOrderOrchestrator;

    @BeforeEach
    void setUp() {
        dto = KitchenOrderDto.builder()
                .orderIdWaiterService(1L)
                .waiterOrderNo(100L)
                .build();

        sendDto = new WaiterOrderSendDto(100L,
                1L,
                List.of());
    }

    /**
     * Проверяет успешную отправку заказа в состояние "готово" и его передачу официанту.
     * <p>
     * given: Заказ существует и его DTO успешно преобразуется в DTO для отправки официанту.
     * when: Вызывается метод setCookedAndSendOrder, который изменяет статус заказа на "готово"
     * и отправляет его официанту.
     * then: Проверяется, что метод sendCookedOrderToWaiter был вызван для отправки заказа официанту.
     */
    @Test
    void testSetCookedAndSendOrder_success() {
        //given
        Long orderId = 1L;
        when(kitchenService.getOrderById(orderId)).thenReturn(dto);
        when(kitchenOrderDtoToWaiterOrderSendDtoMapper.map(dto)).thenReturn(sendDto);

        //when
        kitchenOrderOrchestrator.setCookedAndSendOrder(1L);

        //then
        verify(waiterFeignClient).sendCookedOrderToWaiter(sendDto);
    }

    /**
     * Проверяет поведение системы при возникновении исключения Feign при отправке готового заказа официанту.
     * <p>
     * given: Заказ существует и его DTO успешно преобразуется в DTO для отправки официанту.
     * when: Метод `setCookedAndSendOrder` вызывается, что приводит к попытке отправки заказа,
     * но возникает исключение Feign.
     * then: Ожидается, что будет выброшено исключение `SendOrderFeignException`.
     */
    @Test
    void testSetCookedAndSendOrder_feignException_shouldThrowCustom() {
        //given
        Long orderId = 2L;

        KitchenOrderDto dto = KitchenOrderDto.builder()
                .orderIdWaiterService(orderId)
                .waiterOrderNo(2L)
                .build();

        WaiterOrderSendDto waiterDto = new WaiterOrderSendDto(2L, orderId, List.of());

        when(kitchenService.getOrderById(orderId)).thenReturn(dto);
        when(kitchenOrderDtoToWaiterOrderSendDtoMapper.map(dto)).thenReturn(waiterDto);
        doThrow(mock(FeignException.class)).when(waiterFeignClient).sendCookedOrderToWaiter(waiterDto);

        //when,then
        assertThrows(SendOrderFeignException.class, () -> {
            kitchenOrderOrchestrator.setCookedAndSendOrder(orderId);
        });

    }
}
