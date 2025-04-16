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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void testGetAllOrders_shouldReturnSetOfOrders() {

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

        Set<KitchenOrderResponse> responses = kitchenService.getAllOrders();

        assertNotNull(responses);
        assertEquals(Set.of(kitchenOrderResponse1, kitchenOrderResponse2), responses);
        assertEquals(2, responses.size());
    }

    @Test
    void testGetAllOrders_shouldReturnEmptySet() {

        Set<KitchenOrderResponse> responses = kitchenService.getAllOrders();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }


    @Test
    void testCreateOrder_shouldAcceptOrder_whenDishesAvailable() {
        KitchenOrderMapper kitchenOrderMapper = mock(KitchenOrderMapper.class);
        KitchenDishRepository kitchenDishRepository = mock(KitchenDishRepository.class);
        KitchenDishMapper kitchenDishMapper = mock(KitchenDishMapper.class);
        KitchenOrderToDishRepository kitchenOrderToDishRepository = mock(KitchenOrderToDishRepository.class);
        KitchenOrderDtoToWaiterOrderSendDtoMapper kitchenOrderDtoToWaiterOrderSendDtoMapper = mock(KitchenOrderDtoToWaiterOrderSendDtoMapper.class);
        WaiterFeignClient waiterFeignClient = mock(WaiterFeignClient.class);
        KitchenOrderRepository kitchenOrderRepository = mock(KitchenOrderRepository.class);
        KitchenOrderDtoToResponseMapper kitchenOrderDtoToResponseMapper = mock(KitchenOrderDtoToResponseMapper.class);

        KitchenServiceImpl realService = new KitchenServiceImpl(
                kitchenOrderMapper,
                kitchenDishRepository,
                kitchenDishMapper,
                kitchenOrderToDishRepository,
                kitchenOrderDtoToWaiterOrderSendDtoMapper,
                waiterFeignClient,
                kitchenOrderRepository,
                kitchenOrderDtoToResponseMapper
        );
        KitchenService kitchenServiceSpy = Mockito.spy(realService);
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
        doReturn(true).when(kitchenServiceSpy).dishesIsAvailable(orderDto);
        doNothing().when(kitchenServiceSpy).createOrderToDish(orderDto);
        doNothing().when(kitchenServiceSpy).acceptOrder(orderDto.getOrderIdWaiterService());

        kitchenServiceSpy.createOrder(orderDto);

        verify(kitchenServiceSpy).createOrderToDish(orderDto);
        verify(kitchenServiceSpy).acceptOrder(orderDto.getOrderIdWaiterService());
        verify(kitchenServiceSpy, never()).rejectOrder(orderDto.getOrderIdWaiterService());
    }

    @Test
    void testCreateOrder_shouldRejectOrder_whenDishesIsNotAvailable() {
        KitchenOrderMapper kitchenOrderMapper = mock(KitchenOrderMapper.class);
        KitchenDishRepository kitchenDishRepository = mock(KitchenDishRepository.class);
        KitchenDishMapper kitchenDishMapper = mock(KitchenDishMapper.class);
        KitchenOrderToDishRepository kitchenOrderToDishRepository = mock(KitchenOrderToDishRepository.class);
        KitchenOrderDtoToWaiterOrderSendDtoMapper kitchenOrderDtoToWaiterOrderSendDtoMapper = mock(KitchenOrderDtoToWaiterOrderSendDtoMapper.class);
        WaiterFeignClient waiterFeignClient = mock(WaiterFeignClient.class);
        KitchenOrderRepository kitchenOrderRepository = mock(KitchenOrderRepository.class);
        KitchenOrderDtoToResponseMapper kitchenOrderDtoToResponseMapper = mock(KitchenOrderDtoToResponseMapper.class);

        KitchenServiceImpl realService = new KitchenServiceImpl(
                kitchenOrderMapper,
                kitchenDishRepository,
                kitchenDishMapper,
                kitchenOrderToDishRepository,
                kitchenOrderDtoToWaiterOrderSendDtoMapper,
                waiterFeignClient,
                kitchenOrderRepository,
                kitchenOrderDtoToResponseMapper
        );
        KitchenService kitchenServiceSpy = Mockito.spy(realService);
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

        doReturn(false).when(kitchenServiceSpy).dishesIsAvailable(orderDto);

        doNothing().when(kitchenServiceSpy).rejectOrder(orderDto.getOrderIdWaiterService());

        kitchenServiceSpy.createOrder(orderDto);

        verify(kitchenServiceSpy).rejectOrder(orderDto.getOrderIdWaiterService());
        verify(kitchenServiceSpy, never()).createOrderToDish(orderDto);
        verify(kitchenServiceSpy, never()).acceptOrder(orderDto.getOrderIdWaiterService());
    }

    @Test
    void testAcceptOrder_shouldThrowException_whenOrderIsNotExist() {

        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(false);


        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            kitchenService.acceptOrder(orderId);
        });

        assertEquals(String.format("Order with id %s not found", orderId), exception.getMessage());

        verify(kitchenOrderRepository, never()).updateStatusById(any(), any());
    }

    @Test
    void testAcceptOrder_shouldUpdateStatus_whenOrderIsExist() {
        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(true);

        kitchenService.acceptOrder(orderId);

        verify(kitchenOrderRepository).updateStatusById(orderId, KitchenStatus.ACCEPTED);
    }

    @Test
    void testRejectOrder_shouldRejectOrder_whenOrderExist() {
        KitchenOrderRepository kitchenOrderRepository = mock(KitchenOrderRepository.class);
        KitchenOrderDtoToWaiterOrderSendDtoMapper dtoMapper = mock(KitchenOrderDtoToWaiterOrderSendDtoMapper.class);
        WaiterFeignClient waiterFeignClient = mock(WaiterFeignClient.class);
        KitchenOrderMapper kitchenOrderMapper = mock(KitchenOrderMapper.class);
        KitchenDishRepository kitchenDishRepository = mock(KitchenDishRepository.class);
        KitchenDishMapper kitchenDishMapper = mock(KitchenDishMapper.class);
        KitchenOrderToDishRepository kitchenOrderToDishRepository = mock(KitchenOrderToDishRepository.class);
        KitchenOrderDtoToResponseMapper responseMapper = mock(KitchenOrderDtoToResponseMapper.class);
        KitchenServiceImpl realService = new KitchenServiceImpl(
                kitchenOrderMapper,
                kitchenDishRepository,
                kitchenDishMapper,
                kitchenOrderToDishRepository,
                dtoMapper,
                waiterFeignClient,
                kitchenOrderRepository,
                responseMapper
        );
        KitchenServiceImpl serviceSpy = spy(realService);

        Long orderId = 1L;

        KitchenOrderDto mockDto = new KitchenOrderDto();
        WaiterOrderSendDto waiterOrderSendDto = new WaiterOrderSendDto(1L, 1L, List.of(dishDto));

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(true);

        when(dtoMapper.map(mockDto)).thenReturn(waiterOrderSendDto);

        doReturn(mockDto).when(serviceSpy).getOrderById(orderId);

        waiterFeignClient.sendCanceledOrderToWaiter(waiterOrderSendDto);

        serviceSpy.rejectOrder(orderId);

        verify(kitchenOrderRepository).updateStatusById(orderId, KitchenStatus.REJECTED);
    }

    @Test
    void testRejectOrder_shouldThrowException_whenOrderIsNotExist() {
        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(false);


        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            kitchenService.rejectOrder(orderId);
        });

        assertEquals(String.format("Order with id %s not found", orderId), exception.getMessage());

        verify(kitchenOrderRepository, never()).updateStatusById(any(), any());
    }

    @Test
    void testSetReadyStatus_shouldThrowException_whenOrderIsNotExists() {

        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(false);


        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            kitchenService.setStatusReady(orderId);
        });

        assertEquals(String.format("Order with id %s not found", orderId), exception.getMessage());

        verify(kitchenOrderRepository, never()).updateStatusById(any(), any());
    }

    @Test
    void testSetReadyStatus_shouldUpdateStatus_whenOrderIsExists() {
        Long orderId = 1L;

        when(kitchenOrderRepository.existsById(orderId)).thenReturn(true);

        kitchenService.setStatusReady(orderId);

        verify(kitchenOrderRepository).updateStatusById(orderId, KitchenStatus.COOKED);
    }

    @Test
    void testGetOrderById_shouldReturnOrder_whenOrderIdIsValid() {
        Long orderId = 1L;

        when(kitchenOrderRepository.findById(orderId))
                .thenReturn(Optional.of(kitchenOrder));
        when(kitchenOrderMapper.toDto(kitchenOrder))
                .thenReturn(kitchenOrderDto);

        KitchenOrderDto resultDto = kitchenService.getOrderById(orderId);

        assertNotNull(resultDto);
        assertEquals(kitchenOrderDto, resultDto);
    }

    @Test
    void testGetOrderById_shouldReturnException_whenOrderIdIsNotValid() {
        Long orderId = 1L;

        when(kitchenOrderRepository.findById(orderId)).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            kitchenService.getOrderById(orderId);
        });

        assertEquals("Order with id 1 not found", exception.getMessage());
    }

    @Test
    void testGetDishByShortName_shouldReturnDto_whenDishExists() {
        String shortName = "PIZZA";

        when(kitchenDishRepository.findByShortName(shortName)).thenReturn(Optional.of(dish));
        when(kitchenDishMapper.toDto(dish)).thenReturn(dishDto);

        DishDto resultDto = kitchenService.getDishByShortName(shortName);

        assertNotNull(resultDto);
        assertEquals(dishDto, resultDto);
    }

    @Test
    void testGetDishByShortName_shouldThrowException_whenDishNotExists() {
        String shortName = "sushi";

        when(kitchenDishRepository.findByShortName(shortName))
                .thenReturn(Optional.empty());

        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () -> {
            kitchenService.getDishByShortName(shortName);
        });

        assertEquals(String.format("Dish with shortName %s not found", shortName), exception.getMessage());
    }

    @Test
    void testGetDishById_shouldReturnDto_whenDishExists() {
        Long dishId = 1L;

        when(kitchenDishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(kitchenDishMapper.toDto(dish)).thenReturn(dishDto);

        DishDto resultDto = kitchenService.getDishById(dishId);

        assertNotNull(resultDto);
        assertEquals(dishDto, resultDto);
    }

    @Test
    void testGetDishById_shouldThrowException_whenDishNotExists() {
        Long dishId = 1L;

        when(kitchenDishRepository.findById(dishId))
                .thenReturn(Optional.empty());

        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () -> {
            kitchenService.getDishById(dishId);
        });

        assertEquals(String.format("Dish with id %s not found", dishId), exception.getMessage());
    }

    @Test
    void testDishesIsAvailable_shouldReturnTrue_whenAllDishesAvailable() {
        DishDto dto = new DishDto(1L, 10L, "PIZZA", "tomato", 1L);
        KitchenOrderDto orderDto = new KitchenOrderDto();
        orderDto.setOrderDishes(Set.of(dto));

        Dish availableDish = new Dish();
        availableDish.setShortName("PIZZA");

        when(kitchenDishRepository.findAllDistinct()).thenReturn(List.of(availableDish));
        when(kitchenDishMapper.toDto(availableDish)).thenReturn(dto);

        assertTrue(kitchenService.dishesIsAvailable(orderDto));
    }

    @Test
    void testDishesIsAvailable_shouldReturnFalse_whenSomeDishesNotAvailable() {
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

        assertFalse(kitchenService.dishesIsAvailable(orderDto));
    }

}