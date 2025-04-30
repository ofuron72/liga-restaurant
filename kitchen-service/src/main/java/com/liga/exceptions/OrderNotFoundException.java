package com.liga.exceptions;

/**
 * Исключение, которое выбрасывается, когда заказ не найден.
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
