package com.liga.exceptions;

/**
 * Исключение, которое выбрасывается, когда блюдо не найдено в системе.
 */
public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(String message) {
        super(message);
    }

    public DishNotFoundException(String message, Throwable cause) {
    }
}
