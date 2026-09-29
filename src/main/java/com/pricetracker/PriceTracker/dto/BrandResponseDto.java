package com.pricetracker.PriceTracker.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BrandResponseDto(
    UUID id,
    String name,
    LocalDateTime createdAt
) {}
