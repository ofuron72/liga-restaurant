package com.liga.dto;

public record DishDto(Long id,
                      Long balance,
                      String shortName,
                      String dishComposition,
                      Long dishesNumber) {
}
