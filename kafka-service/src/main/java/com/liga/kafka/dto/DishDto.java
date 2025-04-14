package com.liga.kafka.dto;

public record DishDto(Long id,
                      Long balance,
                      String shortName,
                      String dishComposition,
                      Long dishesNumber) {
}
