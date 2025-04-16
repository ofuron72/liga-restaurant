package com.liga.service;

import com.liga.converter.WaiterOrderDtoToResponseMapper;
import com.liga.converter.WaiterOrderStatusDtoToResponseMapper;
import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderResponse;
import com.liga.dto.WaiterOrderStatusDto;
import com.liga.dto.WaiterOrderStatusResponse;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.exceptions.StatusNotFoundException;
import com.liga.objects.OrderStatus;
import com.liga.repository.WaiterOrderMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WaiterServiceImplTest {

    @Mock
    private WaiterOrderMapper waiterOrderMapper;

    @Mock
    private WaiterOrderDtoToResponseMapper waiterOrderDtoToResponseMapper;

    @Mock
    private WaiterOrderStatusDtoToResponseMapper waiterOrderStatusDtoToResponseMapper;

    @InjectMocks
    private WaiterServiceImpl waiterService;

    private WaiterOrderDto order;
    private WaiterOrderResponse waiterOrderResponse;
    private WaiterOrderStatusResponse waiterOrderStatusResponse;

    @BeforeEach
    void setUp() {
        order = WaiterOrderDto.builder()
                .id(1L)
                .status(OrderStatus.ACCEPTED)
                .createDttm(OffsetDateTime.now())
                .waiterId(1L)
                .tableNo("A1")
                .build();

        waiterOrderResponse = new WaiterOrderResponse(
                1L,
                OrderStatus.ACCEPTED,
                OffsetDateTime.now(),
                1L,
                "A1"
        );

        waiterOrderStatusResponse = new WaiterOrderStatusResponse(
                OrderStatus.ACCEPTED
        );


    }

    @Test
    void testGetOrderById_shouldReturnOrder_whenOrderIdIsValid() {
        when(waiterOrderMapper.getById(1L)).thenReturn(order);
        when(waiterOrderDtoToResponseMapper.mapDtoToResponse(order))
                .thenReturn(waiterOrderResponse);

        WaiterOrderResponse response = waiterService.getOrderById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals(OrderStatus.ACCEPTED, response.status());
    }

    @Test
    void testGetOrderById_shouldReturnException_whenOrderIdIsNotValid() {
        when(waiterOrderMapper.getById(1L)).thenReturn(null);

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class,
                () -> waiterService.getOrderById(1L));

        assertEquals(String.format("Order with id %s not found", 1L), exception.getMessage());
    }


    @Test
    void testGetAllOrders_shouldReturnSetOfOrders() {

        OffsetDateTime fixedTime = OffsetDateTime.now();
        WaiterOrderDto order1 = WaiterOrderDto.builder()
                .id(1L)
                .status(OrderStatus.ACCEPTED)
                .createDttm(fixedTime)
                .waiterId(1L)
                .tableNo("A1")
                .build();

        WaiterOrderDto order2 = WaiterOrderDto.builder()
                .id(2L)
                .status(OrderStatus.ACCEPTED)
                .createDttm(fixedTime)
                .waiterId(1L)
                .tableNo("A2")
                .build();

        WaiterOrderResponse waiterOrderResponse1 = new WaiterOrderResponse(
                1L,
                OrderStatus.ACCEPTED,
                fixedTime,
                1L,
                "A1"
        );

        WaiterOrderResponse waiterOrderResponse2 = new WaiterOrderResponse(
                2L,
                OrderStatus.ACCEPTED,
                fixedTime,
                1L,
                "A2"
        );

        when(waiterOrderMapper.getAll())
                .thenReturn(Set.of(order1, order2));


        when(waiterOrderDtoToResponseMapper.mapDtoToResponse(order1))
                .thenReturn(waiterOrderResponse1);

        when(waiterOrderDtoToResponseMapper.mapDtoToResponse(order2))
                .thenReturn(waiterOrderResponse2);

        Set<WaiterOrderResponse> responses = waiterService.getAllOrders();

        assertNotNull(responses);
        assertEquals(Set.of(waiterOrderResponse1, waiterOrderResponse2), responses);
        assertEquals(2, responses.size());
    }

    @Test
    void testGetAllOrders_shouldReturnEmptySet() {

        when(waiterOrderMapper.getAll())
                .thenReturn(Set.of());

        Set<WaiterOrderResponse> responses = waiterService.getAllOrders();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void testCreateOrder() {
        OffsetDateTime fixedTime = OffsetDateTime.now();
        WaiterOrderDto createdOrder = WaiterOrderDto.builder()
                .id(1L)
                .waiterId(1L)
                .tableNo("A1")
                .createDttm(fixedTime)
                .status(OrderStatus.ACCEPTED)
                .build();

        var response = waiterService.createOrder(order);
        assertEquals(response.getWaiterId(), createdOrder.getWaiterId());
        assertEquals(response.getTableNo(), createdOrder.getTableNo());
        assertEquals(response.getStatus(), createdOrder.getStatus());
        assertNotNull(response);
    }

    @Test
    void testGetOrderStatus_shouldReturnOrderStatus_whenOrderWithIdIsExist() {
        WaiterOrderStatusResponse waiterOrderStatusResponse = new WaiterOrderStatusResponse(OrderStatus.ACCEPTED);
        WaiterOrderStatusDto waiterOrderStatusDto = new WaiterOrderStatusDto(OrderStatus.ACCEPTED);

        when(waiterOrderMapper.getOrderStatus(1L))
                .thenReturn(waiterOrderStatusDto);
        when(waiterOrderStatusDtoToResponseMapper.map(waiterOrderStatusDto))
                .thenReturn(waiterOrderStatusResponse);

        WaiterOrderStatusResponse response = waiterService.getOrderStatus(1L);

        assertNotNull(response);
        assertEquals(waiterOrderStatusResponse, response);
    }

    @Test
    void testGetOrderStatus_shouldReturnException_whenOrderWithIdIsExist() {
        when(waiterOrderMapper.getOrderStatus(1L)).thenReturn(null);

        StatusNotFoundException exception = assertThrows(StatusNotFoundException.class,
                () -> waiterService.getOrderStatus(1L));

        assertEquals(String.format("Status for order with id %s not found", 1L), exception.getMessage());
    }

    @Test
    void testServeOrder() {
        WaiterOrderDto createdOrder = WaiterOrderDto.builder()
                .id(1L)
                .waiterId(1L)
                .tableNo("A1")
                .status(OrderStatus.ACCEPTED)
                .build();
        waiterService.serveOrder(createdOrder);

        assertEquals(OrderStatus.READY_TO_PICKUP, createdOrder.getStatus());
    }

    @Test
    void testCancelOrder() {
        WaiterOrderDto createdOrder = WaiterOrderDto.builder()
                .id(1L)
                .waiterId(1L)
                .tableNo("A1")
                .status(OrderStatus.ACCEPTED)
                .build();
        waiterService.cancelOrder(createdOrder);

        assertEquals(OrderStatus.REJECTED_BY_THE_KITCHEN, createdOrder.getStatus());
    }
}