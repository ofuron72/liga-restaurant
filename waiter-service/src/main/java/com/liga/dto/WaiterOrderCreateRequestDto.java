package com.liga.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WaiterOrderCreateRequestDto(@NotNull(message = "waiterId must not be null")
                                          Long waiterId,
                                          @NotNull(message = "tableNo must not be null")
                                          String tableNo,
                                          @Size(min = 1, message = "count of dishes must be at least 1")
                                          List<DishSendDto> dishes
) {

}
