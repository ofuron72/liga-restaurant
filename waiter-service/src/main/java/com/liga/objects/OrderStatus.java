package com.liga.objects;

/**
 * enum, представляющий статусы заказа в waiter-service.
 */
public enum OrderStatus {
    ACCEPTED,
    REJECTED_BY_THE_KITCHEN,
    READY_TO_PICKUP,
}
