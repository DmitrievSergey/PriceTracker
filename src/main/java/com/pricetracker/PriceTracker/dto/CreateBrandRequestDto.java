package com.pricetracker.PriceTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBrandRequestDto(
        @NotBlank(message = "Brand name cannot be empty")
        @Size(min = 2, max = 50, message = "Brand name must be between 2 and 50 characters")
        String name
) {
}
