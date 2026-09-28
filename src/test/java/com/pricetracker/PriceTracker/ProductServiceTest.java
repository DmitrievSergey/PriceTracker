package com.pricetracker.PriceTracker;

import com.pricetracker.PriceTracker.entity.Brand;
import com.pricetracker.PriceTracker.entity.Product;
import com.pricetracker.PriceTracker.repository.ProductRepository;
import com.pricetracker.PriceTracker.service.BrandService;
import com.pricetracker.PriceTracker.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandService brandService;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_ShouldSaveProduct_WhenBrandExist() {
        String productName = "Джинсы";
        String sku = "213";
        UUID brandId = com.fasterxml.uuid.Generators.timeBasedEpochGenerator().generate();

        Brand mockBrand = new Brand();
        mockBrand.setId(brandId);

        Mockito.when(brandService.findBrandById(brandId)).thenReturn(mockBrand);
        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .then(invocationOnMock -> invocationOnMock.getArgument(0));

        Product product = productService.createProduct(productName, sku, brandId);

        Assertions.assertNotNull(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(productName, product.getName());
        Assertions.assertEquals(mockBrand, product.getBrand());
    }

}
