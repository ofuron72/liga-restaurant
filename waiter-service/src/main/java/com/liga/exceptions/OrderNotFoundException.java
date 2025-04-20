package com.liga.exceptions;

/**
 * Исключение, выбрасываемое при попытке обращения к несуществующему заказу.
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
