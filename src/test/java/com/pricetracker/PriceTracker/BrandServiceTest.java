package com.pricetracker.PriceTracker;

import com.pricetracker.PriceTracker.entity.Brand;
import com.pricetracker.PriceTracker.exception.BrandAlreadyExistsException;
import com.pricetracker.PriceTracker.repository.BrandRepository;
import com.pricetracker.PriceTracker.service.BrandService;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Test
    void createBrand_ShouldSaveBrand_WhenNameIsUnique() throws BrandAlreadyExistsException {
        String brandName = "Apple";
        Mockito.when(brandRepository.existsByName(brandName)).thenReturn(false);
        Mockito.when(brandRepository.save(Mockito.any(Brand.class))).then(invocation ->
                invocation.getArgument(0));

        Brand createdBrand = brandService.createBrand(brandName);

        Assertions.assertNotNull(createdBrand);
        Assertions.assertNotNull(createdBrand.getId());
        Assertions.assertEquals(brandName, createdBrand.getName());
        Mockito.verify(brandRepository, Mockito.times(1)).save(Mockito.any(Brand.class));
    }

    @Test
    void createBrand_ShouldThrowException_WhenNameAlreadyExists() {
        String brandName = "Nike";

        Mockito.when(brandRepository.existsByName(brandName)).thenReturn(true);

        Assertions.assertThrows(BrandAlreadyExistsException.class, () ->
                brandService.createBrand(brandName));

        Mockito.verify(brandRepository, Mockito.never())
                .save(Mockito.any(Brand.class));
    }
}
