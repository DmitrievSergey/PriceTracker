package com.pricetracker.PriceTracker.controller;

import com.pricetracker.PriceTracker.dto.BrandResponseDto;
import com.pricetracker.PriceTracker.dto.CreateBrandRequestDto;
import com.pricetracker.PriceTracker.entity.Brand;
import com.pricetracker.PriceTracker.exception.BrandAlreadyExistsException;
import com.pricetracker.PriceTracker.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
//TODO: добить остальные ручки
@RestController
@RequestMapping("api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponseDto> createBrand(@RequestBody CreateBrandRequestDto request) throws BrandAlreadyExistsException {
        Brand brand = brandService.createBrand(request.name());
        BrandResponseDto response = new BrandResponseDto(
                brand.getId(), brand.getName(), brand.getCreatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDto> getBrandById(@PathVariable UUID id) {

        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        brandService.deleteBrand(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
