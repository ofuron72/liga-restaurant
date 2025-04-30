package com.liga.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Конфигурация для создания топика Kafka c заданным количеством партиций и реплик.
 */
@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka.topic.name}")
    private String topicName;

    @Value("${kafka.topic.partitions}")
    private Integer partitions;

    @Value("${kafka.topic.replicas}")
    private Integer replicas;


    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }


}
