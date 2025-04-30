package com.liga.dto;

/**
 * DTO, представляющий блюдо на кухне.
 */
public record DishDto(Long id,
                      Long balance,
                      String shortName,
                      String dishComposition,
                      Long dishesNumber) {
}
