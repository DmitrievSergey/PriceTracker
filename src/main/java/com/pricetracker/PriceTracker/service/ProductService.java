package com.pricetracker.PriceTracker.service;

import com.pricetracker.PriceTracker.dto.ProductResponseDto;
import com.pricetracker.PriceTracker.entity.Brand;
import com.pricetracker.PriceTracker.entity.Product;
import com.pricetracker.PriceTracker.exception.EntityNotFoundException;
import com.pricetracker.PriceTracker.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;

    @Transactional
    public Product createProduct(String productName, String sku, UUID brandId) {

        Brand brand = brandService.findBrandById(brandId);


        Product createdProduct = new Product();
        createdProduct.setBrand(brand);
        createdProduct.setName(productName);
        createdProduct.setSku(sku);
        createdProduct.setId(com.fasterxml.uuid.Generators.timeBasedEpochGenerator().generate());

        brand.addProduct(createdProduct);

        return productRepository.save(createdProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(
                product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getSku(),
                        product.getBrand().getId(),
                        product.getBrand().getName()
                )
        ).toList();
    }
}
