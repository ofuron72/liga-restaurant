package com.liga.controllers;

import com.liga.WaiterServiceApplication;
import com.liga.dto.WaiterOrderDto;
import com.liga.objects.OrderStatus;
import com.liga.repository.WaiterOrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.testcontainers.utility.DockerImageName;

import java.time.OffsetDateTime;

@SpringBootTest(classes = {WaiterServiceApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("integration-test")
@TestPropertySource(locations = "classpath:application-integration-test.yaml")
class WaiterOrderControllerTest {

    @Container
    static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:15.1-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @Container
    static KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.2.1"));

    static {
        postgresContainer.start();
        kafkaContainer.start();
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WaiterOrderMapper waiterOrderMapper;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    void getOrderById_shouldReturnOrder_whenOrderIsExists() throws Exception {
        //given
        WaiterOrderDto orderCreated = new WaiterOrderDto(null,
                OrderStatus.ACCEPTED,
                OffsetDateTime.now(),
                1L,
                "A1",
                null);

        //when
        waiterOrderMapper.create(orderCreated);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/waiter/orders/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(OrderStatus.ACCEPTED.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tableNo").value("A1"));
    }

    @Test
    void getOrderById_shouldReturnNotFound_whenOrderDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/waiter/orders/{id}", 100L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}