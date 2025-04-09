-- Удаляем данные из order_to_dish
DELETE
FROM order_to_dish
WHERE kitchen_order_id IN (1, 2, 3);

-- Удаляем данные из kitchen_order
DELETE
FROM kitchen_order
WHERE kitchen_order_id IN (1, 2, 3);

-- Удаляем данные из dish
DELETE
FROM dish
WHERE dish_id IN (1, 2, 3);