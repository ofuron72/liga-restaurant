package com.liga.controllers;

import com.liga.dto.KitchenOrderResponse;
import com.liga.dto.ResponseMessage;
import com.liga.service.KitchenService;
import com.liga.service.orchestrator.KitchenOrderOrchestrator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST-контроллер для обработки заказов на кухне.
 * Обрабатывает запросы для получения и обновления статуса заказов.
 */
@SuppressWarnings("checkstyle:Indentation")
@RestController
@RequestMapping("/api/kitchen/orders")
@RequiredArgsConstructor
public class KitchenOrderController {
    private final KitchenService kitchenService;
    private final KitchenOrderOrchestrator kitchenOrderOrchestrator;

    /**
     * Возвращает список всех заказов на кухне.
     */
    @Operation(
            summary = "get all orders",
            description = "get list of all orders in the kitchen",
            operationId = "getAllKitchenOrders"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = KitchenOrderResponse.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Set<KitchenOrderResponse>> getAllOrders() {
        Set<KitchenOrderResponse> orders = kitchenService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Устанавливает статус заказа как {@code READY} и уведомляет об этом waiter-service.
     * После успешного выполнения статус заказа обновляется и отправляется в соответствующий сервис.
     *
     * @param id идентификатор заказа, статус которого нужно изменить
     */
    @Operation(
            summary = "Set order status to READY",
            description = "Marks the order as ready and sends the updated"
                    + " status to the waiter-service",
            operationId = "setOrderReadyStatus",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order status successfully changed to READY",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseMessage.class),
                                    examples = @ExampleObject(value = "status order with id: "
                                            + "1 changed -> READY")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found"
                    )
            }
    )
    @PostMapping("/{id}/setReady")
    public ResponseEntity<ResponseMessage> setReadyStatus(@PathVariable Long id) {
        kitchenOrderOrchestrator.setCookedAndSendOrder(id);
        return new ResponseEntity<>(new ResponseMessage(String
                .format("status order with id: %d changed -> READY", id)),
                HttpStatus.OK);
    }

}
