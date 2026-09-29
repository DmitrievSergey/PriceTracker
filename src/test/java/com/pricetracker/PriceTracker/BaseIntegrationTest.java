package com.pricetracker.PriceTracker;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class BaseIntegrationTest {
    @Container
    @ServiceConnection // Спринговая магия: сама свяжет конфиги DataSource с этим контейнером!
    protected static final PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:16-alpine");
}
