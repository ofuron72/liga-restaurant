package com.liga.dto;

/**
 * response DTO, содержащий информацию о пунтке в Меню.
 */
public record WaiterMenuItemResponse(
        String dish_name,
        Double dish_cost) {
}
