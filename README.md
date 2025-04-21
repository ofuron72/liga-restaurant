## Проект в рамке стажировки Лига Цифровой Экономики

Система управления рестораном состоит из следующих модулей:

- **liga-internship** - родительский модуль
- **dependency_bom** - pom управления зависимостями
- **kitchen-service** - сервис кухни
- **waiter-service** - сервис официантов

## Стек технологий

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Hibernate**
- **MyBatis**
- **Liquibase**
- **Feign Client**
- **Kafka**
- **MapStruct**
- **Docker**
- **Swagger**
- **JUnit5**

## Склонировать репозиторий
```
git clone https://gitlab.com/megagrebec1999/internship.git
```

## Взаимодействие микросервисов

Cервис water-service отправляет заказы на кухню через Kafka.
Cервис kitchen-service уведомляет о статусе заказа через FeignClient.
Заказ принимается кухней, если в наличии есть все блюда в необходимом количестве.



