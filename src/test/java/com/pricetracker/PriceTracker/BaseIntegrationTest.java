package com.pricetracker.PriceTracker;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class BaseIntegrationTest {
    @Container
    @ServiceConnection // Спринговая магия: сама свяжет конфиги DataSource с этим контейнером!
    protected static final PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:16-alpine");

    @Container
    protected static final KafkaContainer kafka =
            new KafkaContainer("apache/kafka:3.8.0");

    @DynamicPropertySource
    static void overrideKafkaProperties(DynamicPropertyRegistry registry) {
        // Вытаскиваем случайный порт, на котором поднялась тестовая Кафка в Докере,
        // и подставляем его в конфиг Спринга прямо перед запуском теста!
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }
}
