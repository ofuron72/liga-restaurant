package com.liga.exceptions;

/**
 * Исключение, которое выбрасывается при ошибке отправки запроса через Feign.
 */
public class SendOrderFeignException extends RuntimeException {
    public SendOrderFeignException(String message) {
        super(message);
    }

    public SendOrderFeignException(String message, Throwable cause) {
        super(message, cause);
    }
}
