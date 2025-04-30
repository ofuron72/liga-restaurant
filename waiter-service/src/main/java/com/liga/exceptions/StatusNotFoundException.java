package com.liga.exceptions;

/**
 * Исключение, выбрасываемое при попытке обращения к статусу заказа, который не существует.
 */
public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(String message) {
        super(message);
    }

    public StatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
