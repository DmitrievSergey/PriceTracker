package com.pricetracker.PriceTracker.service;

import com.pricetracker.PriceTracker.dto.BrandResponseDto;
import com.pricetracker.PriceTracker.entity.Brand;
import com.pricetracker.PriceTracker.exception.BrandAlreadyExistsException;
import com.pricetracker.PriceTracker.exception.EntityNotFoundException;
import com.pricetracker.PriceTracker.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandService {
    private static final Logger log = LoggerFactory.getLogger(BrandService.class);
    private final BrandRepository brandRepository;

    @Transactional
    public Brand createBrand(String name) throws BrandAlreadyExistsException {
        log.debug("Attempting to create a new brand with name: {}", name);
        if(brandRepository.existsByName(name)) {
            throw new BrandAlreadyExistsException("Brand with name " + name + " already exists");
        }

        Brand brand = new Brand();

        brand.setId(com.fasterxml.uuid.Generators.timeBasedEpochGenerator().generate());
        brand.setName(name);

        return brandRepository.save(brand);
    }

    public Brand findBrandById(UUID brandId) {
            return brandRepository.findById(brandId)
                    .orElseThrow(() -> new EntityNotFoundException("Брэнд с id " + brandId + "не найден"));
    }

    @Transactional(readOnly = true)
    public BrandResponseDto getBrandById(UUID brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found"));

        return new BrandResponseDto(brand.getId(), brand.getName(), brand.getCreatedAt());
    }

    @Transactional
    public void deleteBrand(UUID id) {
        if (!brandRepository.existsById(id)) {
            throw new EntityNotFoundException("Brand not found");
        }
        brandRepository.deleteById(id);
    }
}
