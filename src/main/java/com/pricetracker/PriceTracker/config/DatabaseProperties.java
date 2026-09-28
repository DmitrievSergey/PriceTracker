package com.pricetracker.PriceTracker.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties(prefix = "app.datasource")
@Validated
public record DatabaseProperties(
        @NotBlank(message = "Критическая ошибка: Переменная DB_USERNAME не задана в терминале!")
        String username,

        @NotBlank(message = "Критическая ошибка: Переменная DB_PASSWORD не задана в терминале!")
        String password
) {}
