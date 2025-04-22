package com.liga.exceptions;

/**
 * Исключение, выбрасываемое, когда не сущетсвует официанта с переданным id
 */
public class WaiterNotFoundException extends RuntimeException {
    public WaiterNotFoundException(String message) {
        super(message);
    }
}
