package com.pricetracker.PriceTracker;

import com.pricetracker.PriceTracker.entity.Brand;
import com.pricetracker.PriceTracker.exception.BrandAlreadyExistsException;
import com.pricetracker.PriceTracker.repository.BrandRepository;
import com.pricetracker.PriceTracker.service.BrandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandServiceIntegrationTest extends BaseIntegrationTest{
    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void shouldSaveBrandToRealDatabaseAndFindIt() throws BrandAlreadyExistsException {
        // Arrange
        String brandName = "Xiaomi";

        // Act
        Brand savedBrand = brandService.createBrand(brandName);

        // Assert
        Assertions.assertNotNull(savedBrand.getId());

        // Проверяем, что репозиторий реально видит запись в БД через SQL
        boolean exists = brandRepository.existsByName(brandName);
        Assertions.assertTrue(exists);
    }
}
