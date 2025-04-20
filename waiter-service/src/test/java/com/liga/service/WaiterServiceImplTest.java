package com.liga.service;

import com.liga.converter.WaiterMenuDtoToResponseMapper;
import com.liga.converter.WaiterOrderDtoToResponseMapper;
import com.liga.converter.WaiterOrderStatusDtoToResponseMapper;
import com.liga.dto.WaiterMenuItemDto;
import com.liga.dto.WaiterMenuItemResponse;
import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderResponse;
import com.liga.dto.WaiterOrderStatusDto;
import com.liga.dto.WaiterOrderStatusResponse;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.exceptions.StatusNotFoundException;
import com.liga.objects.OrderStatus;
import com.liga.repository.WaiterMenuMapper;
import com.liga.repository.WaiterOrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WaiterServiceImplTest {

    @Mock
    private WaiterOrderMapper waiterOrderMapper;

    @Mock
    private WaiterOrderDtoToResponseMapper waiterOrderDtoToResponseMapper;

    @Mock
    private WaiterMenuMapper waiterMenuMapper;

    @Mock
    private WaiterMenuDtoToResponseMapper waiterMenuDtoToResponseMapper;

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

    /**
     * Проверяет, что метод getOrderById возвращает корректный объект {@link WaiterOrderResponse},
     * если заказ существует.
     * <p>
     * given: заказ с id 1L существует в системе.
     * when: вызывается метод getOrderById(1L).
     * then: возвращается {@link WaiterOrderResponse} с нужными значениями.
     */
    @Test
    void testGetOrderById_shouldReturnOrder_whenOrderIdIsValid() {
        //given
        when(waiterOrderMapper.getById(1L)).thenReturn(order);
        when(waiterOrderDtoToResponseMapper.mapDtoToResponse(order))
                .thenReturn(waiterOrderResponse);

        //when
        WaiterOrderResponse response = waiterService.getOrderById(1L);

        //then
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals(OrderStatus.ACCEPTED, response.status());
    }

    /**
     * Проверяет, что метод getOrderById выбрасывает {@link OrderNotFoundException},
     * если заказ с указанным id не найден.
     * <p>
     * given: заказ с id 1L не существует в системе.
     * when: вызывается метод getOrderById(1L).
     * then: выбрасывается {@link OrderNotFoundException} с соответствующим сообщением.
     */
    @Test
    void testGetOrderById_shouldReturnException_whenOrderIdIsNotValid() {
        //given
        when(waiterOrderMapper.getById(1L)).thenReturn(null);

        //when
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class,
                () -> waiterService.getOrderById(1L));

        //then
        assertEquals(String.format("Order with id %s not found", 1L), exception.getMessage());
    }


    /**
     * Проверяет, что метод getAllOrders возвращает корректный набор заказов.
     *
     * given: два заказа, возвращаемых маппером waiterOrderMapper.getAll().
     * when: вызывается метод getAllOrders у waiterService.
     * then: возвращаемое множество соответствует ожидаемым данным.
     */
    @Test
    void testGetAllOrders_shouldReturnSetOfOrders() {
        //given

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

        //then
        Set<WaiterOrderResponse> responses = waiterService.getAllOrders();

        //then
        assertNotNull(responses);
        assertEquals(Set.of(waiterOrderResponse1, waiterOrderResponse2), responses);
        assertEquals(2, responses.size());
    }


    /**
     * Проверяет, что метод getAllOrders возвращает пустое множество, если заказов нет.
     *
     * given: пустое множество заказов, возвращаемое маппером waiterOrderMapper.getAll().
     * when: вызывается метод getAllOrders у waiterService.
     * then: возвращаемое множество пустое.
     */
    @Test
    void testGetAllOrders_shouldReturnEmptySet() {

        //given
        when(waiterOrderMapper.getAll())
                .thenReturn(Set.of());

        //when
        Set<WaiterOrderResponse> responses = waiterService.getAllOrders();

        //then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    /**
     * Проверяет создание нового заказа через метод createOrder.
     *
     * given: объект WaiterOrderDto, который представляет новый заказ.
     * when: вызывается метод createOrder у waiterService для создания заказа.
     * then: проверяется, что ответ соответствует созданному заказу.
     */
    @Test
    void testCreateOrder() {
        //given
        OffsetDateTime fixedTime = OffsetDateTime.now();
        WaiterOrderDto createdOrder = WaiterOrderDto.builder()
                .id(1L)
                .waiterId(1L)
                .tableNo("A1")
                .createDttm(fixedTime)
                .status(OrderStatus.ACCEPTED)
                .build();

        //when
        var response = waiterService.createOrder(order);

        //then
        assertEquals(response.getWaiterId(), createdOrder.getWaiterId());
        assertEquals(response.getTableNo(), createdOrder.getTableNo());
        assertEquals(response.getStatus(), createdOrder.getStatus());
        assertNotNull(response);
    }

    /**
     * Проверяет, что метод getOrderStatus возвращает правильный статус заказа, если заказ с указанным id существует.
     *
     * given: создаются необходимые объекты, имитируются ответы мока.
     * when: вызывается метод getOrderStatus для получения статуса заказа.
     * then: проверяется, что возвращаемый ответ соответствует ожидаемому.
     */
    @Test
    void testGetOrderStatus_shouldReturnOrderStatus_whenOrderWithIdIsExist() {
        //given
        WaiterOrderStatusResponse waiterOrderStatusResponse = new WaiterOrderStatusResponse(OrderStatus.ACCEPTED);
        WaiterOrderStatusDto waiterOrderStatusDto = new WaiterOrderStatusDto(OrderStatus.ACCEPTED);

        when(waiterOrderMapper.getOrderStatus(1L))
                .thenReturn(waiterOrderStatusDto);
        when(waiterOrderStatusDtoToResponseMapper.map(waiterOrderStatusDto))
                .thenReturn(waiterOrderStatusResponse);

        //when
        WaiterOrderStatusResponse response = waiterService.getOrderStatus(1L);

        //then
        assertNotNull(response);
        assertEquals(waiterOrderStatusResponse, response);
    }

    /**
     * Проверяет, что метод getOrderStatus выбрасывает исключение, если заказ с указанным id не существует.
     *
     * given: создается настройка для мока, чтобы метод getOrderStatus вернул null.
     * when: вызывается метод getOrderStatus, который должен выбросить исключение.
     * then: проверяется, что выброшено исключение и что его сообщение корректно.
     */
    @Test
    void testGetOrderStatus_shouldReturnException_whenOrderWithIdIsExist() {
        //given
        when(waiterOrderMapper.getOrderStatus(1L)).thenReturn(null);

        //when
        StatusNotFoundException exception = assertThrows(StatusNotFoundException.class,
                () -> waiterService.getOrderStatus(1L));

        //then
        assertEquals(String.format("Status for order with id %s not found", 1L), exception.getMessage());
    }

    /**
     * Проверяет, что метод serveOrder изменяет статус заказа на READY_TO_PICKUP.
     *
     * given: создается новый заказ с определенными значениями.
     * when: вызывается метод serveOrder, который должен изменить статус заказа.
     * then: проверяется, что статус заказа был изменен на READY_TO_PICKUP.
     */
    @Test
    void testServeOrder() {
        //given
        WaiterOrderDto createdOrder = WaiterOrderDto.builder()
                .id(1L)
                .waiterId(1L)
                .tableNo("A1")
                .status(OrderStatus.ACCEPTED)
                .build();
        //when
        waiterService.serveOrder(createdOrder);

        //then
        assertEquals(OrderStatus.READY_TO_PICKUP, createdOrder.getStatus());
    }

    /**
     * Проверяет, что метод cancelOrder изменяет статус заказа на REJECTED_BY_THE_KITCHEN.
     *
     * given: создается новый заказ с начальным статусом ACCEPTED.
     * when: вызывается метод cancelOrder, который должен изменить статус заказа.
     * then: проверяется, что статус заказа был изменен на REJECTED_BY_THE_KITCHEN.
     */
    @Test
    void testCancelOrder() {
        //given
        WaiterOrderDto createdOrder = WaiterOrderDto.builder()
                .id(1L)
                .waiterId(1L)
                .tableNo("A1")
                .status(OrderStatus.ACCEPTED)
                .build();
        //when
        waiterService.cancelOrder(createdOrder);

        //then
        assertEquals(OrderStatus.REJECTED_BY_THE_KITCHEN, createdOrder.getStatus());
    }

    /**
     * Проверяет, что метод getAllMenuItems возвращает корректный набор заказов.
     * <p>
     * given: два пункта меню, возвращаемых маппером waiterOrderMapper.
     * when: вызывается метод getAllMenuItems у waiterService.
     * then: возвращаемое множество соответствует ожидаемым данным.
     */
    @Test
    void testGetAllMenuItems_shouldReturnSetOfMenuItems() {
        //given

        WaiterMenuItemDto createdMenuItem1 = WaiterMenuItemDto.builder()
                .id(1L)
                .dish_name("Pizza")
                .dish_cost(12.0)
                .build();

        WaiterMenuItemDto createdMenuItem2 = WaiterMenuItemDto.builder()
                .id(2L)
                .dish_name("Salad")
                .dish_cost(7.99)
                .build();

        WaiterMenuItemResponse waiterMenuItemResponse1 = new WaiterMenuItemResponse(
                "Pizza",
                12.0
        );

        WaiterMenuItemResponse waiterMenuItemResponse2 = new WaiterMenuItemResponse(
                "Salad",
                7.99
        );

        when(waiterMenuMapper.getAll())
                .thenReturn(Set.of(createdMenuItem1, createdMenuItem2));


        when(waiterMenuDtoToResponseMapper.map(createdMenuItem1))
                .thenReturn(waiterMenuItemResponse1);

        when(waiterMenuDtoToResponseMapper.map(createdMenuItem2))
                .thenReturn(waiterMenuItemResponse2);

        //then
        Set<WaiterMenuItemResponse> responses = waiterService.getAllMenuItem();

        //then
        assertNotNull(responses);
        assertEquals(Set.of(waiterMenuItemResponse1, waiterMenuItemResponse2),
                responses);
        assertEquals(2, responses.size());
    }


    /**
     * Проверяет, что метод getAllMenuItems возвращает пустое множество, если заказов нет.
     * <p>
     * given: пустое множество заказов, возвращаемое маппером waiterMenuMapper.getAll().
     * when: вызывается метод getAllMenuItems у waiterService.
     * then: возвращаемое множество пустое.
     */
    @Test
    void testGetAllMenuItems_shouldReturnEmptySet() {

        //given
        when(waiterMenuMapper.getAll())
                .thenReturn(Set.of());

        //when
        Set<WaiterMenuItemResponse> responses = waiterService.getAllMenuItem();

        //then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }


}