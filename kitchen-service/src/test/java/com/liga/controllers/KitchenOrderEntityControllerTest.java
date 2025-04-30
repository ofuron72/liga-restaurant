package com.liga.controllers;

import com.liga.KitchenServiceApplication;
import com.liga.entities.KitchenOrderEntity;
import com.liga.objects.KitchenStatus;
import com.liga.repository.KitchenOrderRepository;
import org.junit.jupiter.api.BeforeEach;
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


@SpringBootTest(classes = {KitchenServiceApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("integration-test")
@TestPropertySource(locations = "classpath:application-integration-test.yaml")
class KitchenOrderEntityControllerTest {

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
    private KitchenOrderRepository kitchenOrderRepository;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @BeforeEach
    void setup() {
        KitchenOrderEntity kitchenOrderEntity1 = KitchenOrderEntity.builder()
                .orderIdWaiterService(1L)
                .waiterOrderNo(1L)
                .status(KitchenStatus.ACCEPTED)
                .build();
        KitchenOrderEntity kitchenOrderEntity2 = KitchenOrderEntity.builder()
                .orderIdWaiterService(2L)
                .status(KitchenStatus.ACCEPTED)
                .waiterOrderNo(2L)
                .build();
        kitchenOrderRepository.save(kitchenOrderEntity1);
        kitchenOrderRepository.save(kitchenOrderEntity2);
    }


    @Test
    void testGetAllOrders_shouldReturnListOfOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/kitchen/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

    }
}