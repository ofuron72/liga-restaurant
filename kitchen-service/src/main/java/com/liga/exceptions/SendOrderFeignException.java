package com.liga.exceptions;

public class SendOrderFeignException extends RuntimeException {
    public SendOrderFeignException(String message) {
        super(message);
    }

    public SendOrderFeignException(String message, Throwable cause) {
        super(message, cause);
    }
}
