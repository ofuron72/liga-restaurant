package com.liga.service;

import com.liga.converter.WaiterMenuDtoToResponseMapper;
import com.liga.converter.WaiterOrderDtoMapper;
import com.liga.converter.WaiterOrderDtoToResponseMapper;
import com.liga.converter.WaiterOrderRequestToDtoMapper;
import com.liga.converter.WaiterOrderStatusDtoToResponseMapper;
import com.liga.dto.WaiterMenuItemResponse;
import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.dto.WaiterOrderDto;
import com.liga.dto.WaiterOrderResponse;
import com.liga.dto.WaiterOrderStatusResponse;
import com.liga.entities.WaiterOrderEntity;
import com.liga.exceptions.OrderNotFoundException;
import com.liga.exceptions.StatusNotFoundException;
import com.liga.exceptions.WaiterNotFoundException;
import com.liga.objects.OrderStatus;
import com.liga.repository.WaiterAccountMapper;
import com.liga.repository.WaiterMenuMapper;
import com.liga.repository.WaiterOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис для работы с заказами официанта.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private final WaiterOrderMapper waiterOrderMapper;
    private final WaiterOrderDtoMapper waiterOrderDtoMapper;
    private final WaiterOrderDtoToResponseMapper waiterOrderDtoToResponseMapper;
    private final WaiterOrderStatusDtoToResponseMapper waiterOrderStatusDtoToResponseMapper;
    private final WaiterMenuMapper waiterMenuMapper;
    private final WaiterMenuDtoToResponseMapper waiterMenuDtoToResponseMapper;
    private final WaiterAccountMapper waiterAccountMapper;
    private final WaiterOrderRequestToDtoMapper waiterOrderRequestToDtoMapper;

    @Override
    public WaiterOrderResponse getOrderById(Long id) {
        log.debug("trying to get waiter order by id {}", id);
        var result = Optional
                .ofNullable(waiterOrderDtoToResponseMapper
                        .mapDtoToResponse(waiterOrderDtoMapper
                                .toWaiterOrderDto(waiterOrderMapper.getById(id))))
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order with id %s not found", id)));
        log.debug("successfully get waiter order by id {}", id);
        return result;
    }

    @Override
    public Set<WaiterOrderResponse> getAllOrders() {
        log.debug("trying to get waiter orders");
        var result = waiterOrderMapper.getAll()
                .stream()
                .map(waiterOrderDtoMapper::toWaiterOrderDto)
                .map(waiterOrderDtoToResponseMapper::mapDtoToResponse)
                .collect(Collectors.toSet());
        log.debug("successfully get {} orders", result.size());
        return result;
    }

    @Override
    public WaiterOrderDto createOrder(WaiterOrderCreateRequestDto orderRequestDto) {
        log.debug("trying to create order {}", orderRequestDto);

        WaiterOrderEntity order = waiterOrderDtoMapper
                .toWaiterOrderEntity(waiterOrderRequestToDtoMapper
                        .map(orderRequestDto));
        if (!waiterAccountMapper.existsById(order.getWaiterId())) {
            throw new WaiterNotFoundException("Waiter not found with ID:"
                    + order.getWaiterId());
        }
        order.setStatus(OrderStatus.ACCEPTED);
        order.setCreateDttm(OffsetDateTime.now());
        waiterOrderMapper.create(order);
        var result = waiterOrderDtoMapper.toWaiterOrderDto(order);
        log.debug("Order created: {}", order);
        return result;
    }

    @Override
    public WaiterOrderStatusResponse getOrderStatus(Long id) {
        log.debug("trying to get waiter order status by id {}", id);
        var result = Optional
                .ofNullable(waiterOrderStatusDtoToResponseMapper.map(waiterOrderMapper.getOrderStatus(id)))
                .orElseThrow(() -> new StatusNotFoundException(String.format("Status for order with id %s not found", id)));
        log.debug("successfully get waiter order status by id {}", id);
        return result;
    }

    /**
     * Изменяет статус заказа на "готов к получению" и обновляет информацию о заказе.
     */
    @Override
    public void serveOrder(WaiterOrderDto orderRequestDto) {
        log.debug("trying to serve order {}", orderRequestDto);

        WaiterOrderEntity order = waiterOrderDtoMapper.toWaiterOrderEntity(orderRequestDto);

        order.setStatus(OrderStatus.READY_TO_PICKUP);
        waiterOrderMapper.updateStatusOrder(order);
        log.debug("Order with id={} ready to pickup", order.getId());
    }

    /**
     * Отменяет заказ, изменяя его статус на "отклонён" со стороны кухни
     * и обновляет информацию о заказе.
     */
    @Override
    public void cancelOrder(WaiterOrderDto orderRequestDto) {
        log.debug("trying to cancel order {}", orderRequestDto);

        WaiterOrderEntity order = waiterOrderDtoMapper
                .toWaiterOrderEntity(orderRequestDto);

        order.setStatus(OrderStatus.REJECTED_BY_THE_KITCHEN);
        waiterOrderMapper.updateStatusOrder(order);
        log.debug("Order with id={} rejected by the kitchen", order.getId());
    }

    @Override
    public Set<WaiterMenuItemResponse> getAllMenuItem() {
        log.debug("trying to get menu items");
        var result = waiterMenuMapper.getAll()
                .stream()
                .map(waiterMenuDtoToResponseMapper::map)
                .collect(Collectors.toSet());
        log.debug("successfully get {} menuItems", result.size());
        return result;
    }
}
