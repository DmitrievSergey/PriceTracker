package com.pricetracker.PriceTracker.dto;

import java.util.UUID;

public record ProductResponseDto(
    UUID id,
    String name,
    String sku,
    UUID brandId,
    String brandName
) {}
