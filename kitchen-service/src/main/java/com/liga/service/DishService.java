package com.liga.service;

import com.liga.dto.DishDto;

public interface DishService {
    DishDto getDishById(Long orderId);
    DishDto getDishByShortName(String dishName);
}
