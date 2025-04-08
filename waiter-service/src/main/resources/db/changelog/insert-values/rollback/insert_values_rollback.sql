-- Удаляем данные из order_positions
DELETE
FROM order_positions
WHERE order_no IN (1, 2, 3);

-- Удаляем данные из payment
DELETE
FROM payment
WHERE order_no IN (1, 2, 3);

-- Удаляем данные из waiter_order
DELETE
FROM waiter_order
WHERE order_no IN (1, 2, 3);

-- Удаляем данные из waiter_account
DELETE
FROM waiter_account
WHERE waiter_id IN (1, 2, 3);

-- Удаляем данные из menu
DELETE
FROM menu
WHERE id IN (1, 2, 3);