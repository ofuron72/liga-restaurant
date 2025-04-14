package com.liga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WaiterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WaiterServiceApplication.class, args);
    }
}
