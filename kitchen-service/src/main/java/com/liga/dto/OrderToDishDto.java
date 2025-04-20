package com.liga.dto;

/**
 * DTO, представляющий связь между заказом и блюдом.
 */
public record OrderToDishDto(Long kitchenOrderId,
                             Long dishId,
                             Long dishesNumber) {
}
