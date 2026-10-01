package com.pricetracker.PriceTracker.dto;

import java.util.UUID;

public record PriceParseTaskDto(
        UUID productId,
        String url,
        String platform
) {
}
