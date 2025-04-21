package com.liga.controllers;


import com.liga.dto.WaiterMenuItemResponse;
import com.liga.dto.WaiterOrderCreateRequestDto;
import com.liga.entities.WaiterOrder;
import com.liga.dto.WaiterOrderResponse;
import com.liga.dto.WaiterOrderStatusResponse;
import com.liga.service.WaiterService;
import com.liga.service.orchestrator.WaiterOrderOrchestrator;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

@RestController
@RequestMapping("/api/waiter/orders")
@RequiredArgsConstructor
public class WaiterOrderController {
    private final WaiterService waiterService;
    private final WaiterOrderOrchestrator waiterOrderOrchestrator;


    @Operation(
            summary = "order creating",
            description = "Allows you to place a new order with the waiter",
            operationId = "CreateOrder",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "orderDetails",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WaiterOrderCreateRequestDto.class),
                            examples = @ExampleObject(value = "classpath:/endpoints/order-creating-example.json")
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    }
    )
    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid WaiterOrderCreateRequestDto waiterOrderDto) {

        waiterOrderOrchestrator.saveAndSend(waiterOrderDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all orders",
            description = "Returns a set of all orders",
            operationId = "getAllOrders"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of orders returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = WaiterOrderResponse.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Set<WaiterOrderResponse>> getAllOrders() {
        Set<WaiterOrderResponse> ordersDto = waiterService.getAllOrders();
        return new ResponseEntity<>(ordersDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all menuItems",
            description = "Returns a set of all menuItems",
            operationId = "getAllMenuItems"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of menuItems returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = WaiterMenuItemResponse.class))
                    )
            )
    })
    @GetMapping("/menu")
    public ResponseEntity<Set<WaiterMenuItemResponse>> getAllMenuItems() {
        Set<WaiterMenuItemResponse> menuItemResponses = waiterService.getAllMenuItem();
        return new ResponseEntity<>(menuItemResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get order by ID",
            description = "Retrieves a single order by id",
            operationId = "getOrderById",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the order to retrieve",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", format = "int64")
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WaiterOrderResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<WaiterOrderResponse> getOrderById(@PathVariable Long id) {
        WaiterOrderResponse waiterOrderResponse = waiterService.getOrderById(id);
        return new ResponseEntity<>(waiterOrderResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Get order status",
            description = "Returns the status of the order with ID.",
            operationId = "getOrderStatusById",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the order",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", format = "int64")
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order status retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WaiterOrderStatusResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found"
            )
    })
    @GetMapping("/status/{id}")
    public ResponseEntity<WaiterOrderStatusResponse> getOrderStatusById(@PathVariable Long id) {
        WaiterOrderStatusResponse waiterOrderResponse = waiterService.getOrderStatus(id);
        return new ResponseEntity<>(waiterOrderResponse, HttpStatus.OK);
    }


    /**
     * endpoint для feign,
     *
     * @param waiterOrder dto с данными о готовом заказе
     */
    @Hidden
    @PostMapping("/cooked")
    public ResponseEntity<Void> receiveCookedOrderFromKitchen(@RequestBody WaiterOrder waiterOrder) {
        waiterService.serveOrder(waiterOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * endpoint для feign,
     *
     * @param waiterOrder dto с данными об отмененном заказе
     */
    @Hidden
    @PostMapping("/rejected")
    public ResponseEntity<Void> receiveRejectedOrderFromKitchen(@RequestBody WaiterOrder waiterOrder) {
        waiterService.cancelOrder(waiterOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
