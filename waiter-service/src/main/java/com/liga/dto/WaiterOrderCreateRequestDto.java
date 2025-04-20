package com.liga.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO-запрос для создания заказа в сервисе официанта.
 */
public record WaiterOrderCreateRequestDto(@NotNull(message = "waiterId must not be null")
                                          Long waiterId,
                                          @NotNull(message = "tableNo must not be null")
                                          String tableNo,
                                          @Size(min = 1, message = "count of dishes must be at least 1")
                                          List<DishSendDto> dishes
) {

}
