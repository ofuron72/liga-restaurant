spring-boot-starter-actuator у нас будет использоваться всеми модулями, 
поэтому мы размещаем его в общем pom.
В то же время spring-boot-starter-web и lombok размещаем в pom 
waiter-service и kitchen-service (предполагаем, что в будущем будут добавлены еще модули,
для которых эти зависимости не требуются)