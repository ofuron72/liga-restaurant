INSERT INTO dish (dish_id, balance, short_name, dish_composition)
VALUES (nextval('dish_sequence'), 100, 'Pizza', 'Dough, sauce, cheese, vegetables'),
       (nextval('dish_sequence'), 50, 'Pasta', 'Noodles, sauce, meat'),
       (nextval('dish_sequence'), 200, 'Salad', 'Leaves, vegetables, fruits, dressing');