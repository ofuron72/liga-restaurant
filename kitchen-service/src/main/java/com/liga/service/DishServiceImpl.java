package com.liga.service;

import com.liga.converter.KitchenDishMapper;
import com.liga.dto.DishDto;
import com.liga.exceptions.DishNotFoundException;
import com.liga.repository.KitchenDishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final KitchenDishRepository kitchenDishRepository;
    private final KitchenDishMapper kitchenDishMapper;

    @Override
    public DishDto getDishById(Long id) {
        log.debug("trying to get Dish by id");
        var result = kitchenDishMapper.toDto(kitchenDishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(String.format("Dish with id %s not found", id))));
        log.debug("successfully retrieved dish with id: {}", id);
        return result;
    }

    @Override
    public DishDto getDishByShortName(String shortName) {
        log.debug("trying to get dish by short name");
        var result = kitchenDishMapper.toDto(kitchenDishRepository.findByShortName(shortName)
                .orElseThrow(() -> new DishNotFoundException(String.format("Dish with shortName %s not found", shortName))));
        log.debug("successfully retrieved dish with shortname: {}", shortName);
        return result;
    }
}
