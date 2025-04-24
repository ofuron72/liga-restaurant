package com.liga.service;

import com.liga.converter.KitchenDishMapper;
import com.liga.converter.KitchenOrderDtoToResponseMapper;
import com.liga.converter.KitchenOrderDtoToWaiterOrderSendDtoMapper;
import com.liga.converter.KitchenOrderMapper;
import com.liga.dto.DishDto;
import com.liga.dto.KitchenOrderDto;
import com.liga.dto.KitchenOrderResponse;
import com.liga.dto.WaiterOrderSendDto;
import com.liga.entities.Dish;
import com.liga.entities.KitchenOrder;
import com.liga.entities.OrderToDish;
import com.liga.exceptions.DishNotFoundException;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.feign.WaiterFeignClient;
import com.liga.objects.KitchenStatus;
import com.liga.repository.KitchenDishRepository;
import com.liga.repository.KitchenOrderRepository;
import com.liga.repository.KitchenOrderToDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KitchenServiceImplTest {
    @Mock
    private KitchenOrderMapper kitchenOrderMapper;
    @Mock
    private KitchenDishRepository kitchenDishRepository;
    @Mock
    private KitchenDishMapper kitchenDishMapper;
    @Mock
    private KitchenOrderToDishRepository kitchenOrderToDishRepository;
    @Mock
    private KitchenOrderDtoToWaiterOrderSendDtoMapper kitchenOrderDtoToWaiterOrderSendDtoMapper;
    @Mock
    private WaiterFeignClient waiterFeignClient;
    @Mock
    private KitchenOrderRepository kitchenOrderRepository;
    @Mock
    private KitchenOrderDtoToResponseMapper kitchenOrderDtoToResponseMapper;

    @Spy
    @InjectMocks
    private KitchenServiceImpl serviceSpy;


    @InjectMocks
    private KitchenServiceImpl kitchenService;

    private KitchenOrder kitchenOrder;
    private KitchenOrderDto kitchenOrderDto;
    private DishDto dishDto;
    private Dish dish;

    @BeforeEach
    void setUp() {
        OrderToDish orderToDish = new OrderToDish();
        kitchenOrder = KitchenOrder.builder()
                .id(1L)
                .orderIdWaiterService(1L)
                .waiterOrderNo(1L)
                .orderDishes(Set.of(orderToDish))
                .build();
        kitchenOrderDto = KitchenOrderDto
                .builder()
                .id(1L)
                .orderIdWaiterService(1L)
                .waiterOrderNo(1L)
                .build();
        kitchenOrderDto.setOrderIdWaiterService(1L);
        dishDto = new DishDto(1L,
                10L,
                "PIZZA",
                "tomato, cheese",
                1L
        );
        kitchenOrderDto.setOrderDishes(Set.of(dishDto));
        dish = Dish.builder()
                .id(1L)
                .balance(10L)
                .dishComposition("tomato, cheese")
                .shortName("PIZZA")
                .build();
    }

    /**
     * Проверяет получение всех заказов с кухни.
     * given: В репозитории есть два заказа, мапперы возвращают корректные DTO и респонсы.
     * when: Вызывается метод getAllOrders сервиса.
     * then: Возвращается Set, содержащий два KitchenOrderResponse, соответствующих заказам.
     */
    @Test
    void testGetAllOrders_shouldReturnSetOfOrders() {
        //given
        KitchenOrder order1 = KitchenOrder.builder()
                .id(1L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(1L)
                .orderIdWaiterService(1L)
                .build();

        KitchenOrder order2 = KitchenOrder.builder()
                .id(2L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(2L)
                .orderIdWaiterService(2L)
                .build();

        KitchenOrderDto orderDto1 = KitchenOrderDto.builder()
                .id(1L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(1L)
                .orderIdWaiterService(1L)
                .build();

        KitchenOrderDto orderDto2 = KitchenOrderDto.builder()
                .id(2L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(2L)
                .orderIdWaiterService(2L)
                .build();

        KitchenOrderResponse kitchenOrderResponse1 = new KitchenOrderResponse(
                1L,
                1L,
                KitchenStatus.ACCEPTED,
                1L
        );

        KitchenOrderResponse kitchenOrderResponse2 = new KitchenOrderResponse(
                2L,
                2L,
                KitchenStatus.ACCEPTED,
                2L
        );

        when(kitchenOrderRepository.findAllDistinct())
                .thenReturn(List.of(order1, order2));

        when(kitchenOrderMapper.toDto(order1))
                .thenReturn(orderDto1);

        when(kitchenOrderMapper.toDto(order2))
                .thenReturn(orderDto2);

        when(kitchenOrderDtoToResponseMapper.mapDtoToResponse(orderDto1))
                .thenReturn(kitchenOrderResponse1);
        when(kitchenOrderDtoToResponseMapper.mapDtoToResponse(orderDto2))
                .thenReturn(kitchenOrderResponse2);

        //when
        Set<KitchenOrderResponse> responses = kitchenService.getAllOrders();

        //then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(Set.of(kitchenOrderResponse1, kitchenOrderResponse2), responses);
    }

    /**
     * Проверяет поведение сервиса при отсутствии заказов.
     * given: Репозиторий возвращает пустой список заказов.
     * when: Вызывается метод getAllOrders сервиса.
     * then: Возвращается пустой Set без ошибок.
     */
    @Test
    void testGetAllOrders_shouldReturnEmptySet() {
        //given
        when(kitchenOrderRepository.findAllDistinct()).thenReturn(Collections.emptyList());

        //when
        Set<KitchenOrderResponse> responses = kitchenService.getAllOrders();

        //then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    /**
     * Проверяет создание и принятие заказа, когда блюда доступны.
     * given: Заказ с блюдами, которые доступны в системе.
     * when:Вызывается метод createOrder для создания заказа.
     * then: Заказ создается, и вызываются методы для создания позиций в заказе и принятия заказа.
     * Метод rejectOrder не должен быть вызван, так как блюда доступны.
     */
    @Test
    void testCreateOrder_shouldAcceptOrder_whenDishesAvailable() {
        //given
        KitchenOrder order = KitchenOrder.builder()
                .id(1L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(1L)
                .orderIdWaiterService(1L)
                .build();

        KitchenOrderDto orderDto = KitchenOrderDto.builder()
                .id(1L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(1L)
                .orderIdWaiterService(1L)
                .orderDishes(Set.of(dishDto))
                .build();


        when(kitchenOrderMapper.toEntity(orderDto)).thenReturn(order);
        when(kitchenOrderRepository.save(any())).thenReturn(order);
        doReturn(true).when(serviceSpy).dishesIsAvailable(orderDto);
        doNothing().when(serviceSpy).createOrderToDish(orderDto);
        doNothing().when(serviceSpy).acceptOrder(orderDto.getOrderIdWaiterService());

        //when
        serviceSpy.createOrder(orderDto);

        //then
        verify(serviceSpy).createOrderToDish(orderDto);
        verify(serviceSpy).acceptOrder(orderDto.getOrderIdWaiterService());
        verify(serviceSpy, never()).rejectOrder(orderDto.getOrderIdWaiterService());
    }

    /**
     * Проверяет создание заказа и отклонение, когда блюда недоступны.
     * given: Заказ с блюдами, которые недоступны в системе.
     * when: Вызывается метод createOrder для создания заказа.
     * then: Заказ отклоняется, и вызывается метод rejectOrder.
     * Методы createOrderToDish и acceptOrder не должны быть вызваны, так как блюда недоступны.
     */
    @Test
    void testCreateOrder_shouldRejectOrder_whenDishesIsNotAvailable() {
        //given
        KitchenOrder order = KitchenOrder.builder()
                .id(1L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(1L)
                .orderIdWaiterService(1L)
                .build();

        KitchenOrderDto orderDto = KitchenOrderDto.builder()
                .id(1L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(1L)
                .orderIdWaiterService(1L)
                .orderDishes(Set.of(dishDto))
                .build();


        when(kitchenOrderMapper.toEntity(orderDto)).thenReturn(order);
        when(kitchenOrderRepository.save(any())).thenReturn(order);

        doReturn(false).when(serviceSpy).dishesIsAvailable(orderDto);

        doNothing().when(serviceSpy).rejectOrder(orderDto.getOrderIdWaiterService());

        //when
        serviceSpy.createOrder(orderDto);

        //then
        verify(serviceSpy).rejectOrder(orderDto.getOrderIdWaiterService());
        verify(serviceSpy, never()).createOrderToDish(orderDto);
        verify(serviceSpy, never()).acceptOrder(orderDto.getOrderIdWaiterService());
    }

    /**
     * Проверяет поведение сервиса при попытке принятия заказа, которого не существует.
     * given: Заказ с указанным ID отсутствует в репозитории.
     * when: Вызывается метод acceptOrder с данным ID заказа.
     * then: Бросается исключение OrderNotFoundException с сообщением, что заказ не найден.
     * Метод updateStatusById не должен быть вызван.
     */
    @Test
    void testAcceptOrder_shouldThrowException_whenOrderIsNotExist() {
        //given
        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(false);

        //when
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            kitchenService.acceptOrder(orderId);
        });

        //then
        assertEquals(String.format("Order with id %s not found", orderId), exception.getMessage());

        verify(kitchenOrderRepository, never()).updateStatusById(any(), any());
    }

    /**
     * Проверяет поведение сервиса при принятии заказа, который существует.
     * given: Заказ с указанным ID существует в репозитории.
     * when: Вызывается метод acceptOrder с данным ID заказа.
     * when: Статус заказа обновляется на ACCEPTED, и метод updateStatusById вызывается с правильными параметрами.
     */
    @Test
    void testAcceptOrder_shouldUpdateStatus_whenOrderIsExist() {
        //given
        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(true);

        //when
        kitchenService.acceptOrder(orderId);

        //then
        verify(kitchenOrderRepository).updateStatusById(orderId, KitchenStatus.ACCEPTED);
    }

    /**
     * Проверяет поведение сервиса при отклонении заказа, который существует.
     * given: Заказ с указанным ID существует в репозитории,
     * и для этого заказа можно получить данные через маппер.
     * when: Вызывается метод rejectOrder с данным ID заказа, и отправляется информация об отмене заказа в сервис официантов.
     * then: Статус заказа обновляется на REJECTED, и метод updateStatusById вызывается с правильными параметрами.
     */
    @Test
    void testRejectOrder_shouldRejectOrder_whenOrderExist() {
        //given
        Long orderId = 1L;

        KitchenOrderDto mockDto = new KitchenOrderDto();
        WaiterOrderSendDto waiterOrderSendDto = new WaiterOrderSendDto(1L, 1L, List.of(dishDto));

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(true);

        when(kitchenOrderDtoToWaiterOrderSendDtoMapper
                .map(mockDto)).thenReturn(waiterOrderSendDto);

        doReturn(mockDto).when(serviceSpy).getOrderById(orderId);

        //when
        waiterFeignClient.sendCanceledOrderToWaiter(waiterOrderSendDto);

        serviceSpy.rejectOrder(orderId);

        //then
        verify(kitchenOrderRepository).updateStatusById(orderId, KitchenStatus.REJECTED);
    }

    /**
     * Проверяет поведение сервиса при попытке отклонения заказа, который не существует.
     * given: Заказ с указанным ID не существует в репозитории.
     * when: Вызывается метод rejectOrder с данным ID заказа.
     * then: Бросается исключение OrderNotFoundException с правильным сообщением, и метод updateStatusById не вызывается.
     */
    @Test
    void testRejectOrder_shouldThrowException_whenOrderIsNotExist() {
        //given
        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(false);

        //when
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            kitchenService.rejectOrder(orderId);
        });

        //then
        assertEquals(String.format("Order with id %s not found", orderId), exception.getMessage());

        verify(kitchenOrderRepository, never()).updateStatusById(any(), any());
    }

    /**
     * Проверяет поведение сервиса при попытке установить статус "READY" для несуществующего заказа.
     * given: Заказ с указанным ID не существует в репозитории.
     * when: Вызывается метод setStatusReady с данным ID заказа.
     * then: Бросается исключение OrderNotFoundException с правильным сообщением, и метод updateStatusById не вызывается.
     */
    @Test
    void testSetReadyStatus_shouldThrowException_whenOrderIsNotExists() {
        //given
        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(false);

        //when
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            kitchenService.setStatusReady(orderId);
        });

        //then
        assertEquals(String.format("Order with id %s not found", orderId), exception.getMessage());

        verify(kitchenOrderRepository, never()).updateStatusById(any(), any());
    }

    /**
     * Проверяет поведение сервиса при успешной установке статуса "READY" для существующего заказа.
     * given: Заказ с указанным ID существует в репозитории.
     * when: Вызывается метод setStatusReady с данным ID заказа.
     * then: Статус заказа обновляется на "COOKED" с помощью вызова метода updateStatusById.
     */
    @Test
    void testSetReadyStatus_shouldUpdateStatus_whenOrderIsExists() {
        //given
        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(true);

        //when
        kitchenService.setStatusReady(orderId);

        //then
        verify(kitchenOrderRepository).updateStatusById(orderId, KitchenStatus.COOKED);
    }

    /**
     * Проверяет поведение сервиса при получении заказа по его ID.
     * given: В репозитории существует заказ с указанным ID.
     * when: Вызывается метод getOrderById с данным ID заказа.
     * then: Метод возвращает соответствующий объект KitchenOrderDto.
     */
    @Test
    void testGetOrderById_shouldReturnOrder_whenOrderIdIsValid() {

        //given
        Long orderId = 1L;

        when(kitchenOrderRepository.findById(orderId))
                .thenReturn(Optional.of(kitchenOrder));
        when(kitchenOrderMapper.toDto(kitchenOrder))
                .thenReturn(kitchenOrderDto);

        //when
        KitchenOrderDto resultDto = kitchenService.getOrderById(orderId);

        //then
        assertNotNull(resultDto);
        assertEquals(kitchenOrderDto, resultDto);
    }

    /**
     * Проверяет поведение сервиса при попытке получить заказ по несуществующему ID.
     * given: В репозитории отсутствует заказ с указанным ID.
     * when: Вызывается метод getOrderById с несуществующим ID заказа.
     * then: Ожидается, что будет выброшено исключение OrderNotFoundException с соответствующим сообщением.
     */
    @Test
    void testGetOrderById_shouldReturnException_whenOrderIdIsNotValid() {
        //given
        Long orderId = 1L;

        when(kitchenOrderRepository.findById(orderId)).thenReturn(Optional.empty());

        //when
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            kitchenService.getOrderById(orderId);
        });

        //then
        assertEquals("Order with id 1 not found", exception.getMessage());
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
        DishDto resultDto = kitchenService.getDishByShortName(shortName);

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
            kitchenService.getDishByShortName(shortName);
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
        DishDto resultDto = kitchenService.getDishById(dishId);

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
            kitchenService.getDishById(dishId);
        });

        //then
        assertEquals(String.format("Dish with id %s not found", dishId), exception.getMessage());
    }

    /**
     * Проверяет, что метод dishesIsAvailable возвращает true, если все блюда доступны.
     * given: Репозиторий возвращает список доступных блюд, и маппер правильно преобразует блюдо в DTO.
     * when: Вызывается метод dishesIsAvailable с заказом, содержащим одно блюдо.
     * then: Метод возвращает true, так как все блюда доступны.
     */
    @Test
    void testDishesIsAvailable_shouldReturnTrue_whenAllDishesAvailable() {
        //given
        DishDto dto = new DishDto(1L, 10L, "PIZZA", "tomato", 1L);
        KitchenOrderDto orderDto = new KitchenOrderDto();
        orderDto.setOrderDishes(Set.of(dto));

        Dish availableDish = new Dish();
        availableDish.setShortName("PIZZA");

        when(kitchenDishRepository.findAllDistinct()).thenReturn(List.of(availableDish));
        when(kitchenDishMapper.toDto(availableDish)).thenReturn(dto);

        //when
        boolean result = kitchenService.dishesIsAvailable(orderDto);

        //then
        assertTrue(result);
    }

    /**
     * Проверяет, что метод dishesIsAvailable возвращает false, если хотя бы одно блюдо не доступно.
     * given: Репозиторий возвращает список доступных блюд, но заказ содержит блюдо, которого нет в списке доступных.
     * when: Вызывается метод dishesIsAvailable с заказом, содержащим недоступное блюдо.
     * then: Метод возвращает false, так как одно или несколько блюд из заказа недоступны.
     */
    @Test
    void testDishesIsAvailable_shouldReturnFalse_whenSomeDishesNotAvailable() {
        //given
        DishDto orderedDish = new DishDto(2L, 2L, "salad",
                "tomato", 2L);
        KitchenOrderDto orderDto = new KitchenOrderDto();
        orderDto.setOrderDishes(Set.of(orderedDish));

        Dish availableDish = new Dish();
        availableDish.setShortName("pizza");

        when(kitchenDishRepository.findAllDistinct()).thenReturn(List.of(availableDish));
        when(kitchenDishMapper.toDto(availableDish)).thenReturn(
                new DishDto(1L, 10L, "pizza", "tomato", 1L)
        );

        //when
        boolean result = kitchenService.dishesIsAvailable(orderDto);

        //then
        assertFalse(result);
    }

}