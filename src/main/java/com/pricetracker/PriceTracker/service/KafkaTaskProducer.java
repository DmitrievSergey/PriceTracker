package com.pricetracker.PriceTracker.service;

import com.pricetracker.PriceTracker.dto.PriceParseTaskDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaTaskProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaTaskProducer.class);

    private final KafkaTemplate<String, PriceParseTaskDto> kafkaTemplate;

    public void sendParseTask(PriceParseTaskDto task) {
        String topic = "price-parsing-tasks";
        log.info("Sending parse task to Kafka topic '{}' for product: {}", topic, task.productId());

        // Отправляем сообщение в топик
        kafkaTemplate.send(topic, task);
    }
}
