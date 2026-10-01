package com.pricetracker.PriceTracker;

import com.pricetracker.PriceTracker.dto.PriceParseTaskDto;
import com.pricetracker.PriceTracker.service.KafkaTaskProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;


import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KafkaTaskProducerIntegrationTest extends BaseIntegrationTest{
    @Autowired
    private KafkaTaskProducer kafkaTaskProducer;

    @Test
    void shouldSendParseTaskToKafkaTopicCorrectly() {
        // Arrange (Готовим данные)
        UUID productId = UUID.randomUUID();
        PriceParseTaskDto expectedTask = new PriceParseTaskDto(
                productId,
                "https://wildberries.ru",
                "WILDBERRIES"
        );

        // Настраиваем тестового потребителя прямо внутри теста, чтобы прочитать топик
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-verification-group");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        // Разрешаем десериализацию нашего DTO класса
        consumerProps.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "com.pricetracker.PriceTracker.dto");

        DefaultKafkaConsumerFactory<String, PriceParseTaskDto> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, PriceParseTaskDto> testConsumer = consumerFactory.createConsumer();

        // Подписываемся на наш топик
        testConsumer.subscribe(Collections.singletonList("price-parsing-tasks"));

        // Act (Выполняем отправку через наш сервис)
        kafkaTaskProducer.sendParseTask(expectedTask);

        // Assert (Вытаскиваем сообщение из Кафки и проверяем его содержимое)
        ConsumerRecord<String, PriceParseTaskDto> receivedRecord =
                KafkaTestUtils.getSingleRecord(testConsumer, "price-parsing-tasks", Duration.ofSeconds(5));

        Assertions.assertNotNull(receivedRecord);
        PriceParseTaskDto actualTask = receivedRecord.value();

        Assertions.assertEquals(expectedTask.productId(), actualTask.productId());
        Assertions.assertEquals(expectedTask.url(), actualTask.url());
        Assertions.assertEquals(expectedTask.platform(), actualTask.platform());

        // Закрываем консьюмера
        testConsumer.close();
    }
}
