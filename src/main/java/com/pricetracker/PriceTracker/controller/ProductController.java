package com.pricetracker.PriceTracker.controller;

import com.pricetracker.PriceTracker.dto.PriceParseTaskDto;
import com.pricetracker.PriceTracker.dto.ProductResponseDto;
import com.pricetracker.PriceTracker.service.KafkaTaskProducer;
import com.pricetracker.PriceTracker.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final KafkaTaskProducer kafkaTaskProducer;


    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/test-kafka")
    public ResponseEntity<Void> testKafka(@RequestBody PriceParseTaskDto task) {
        kafkaTaskProducer.sendParseTask(task);
        return ResponseEntity.ok().build();
    }
}
