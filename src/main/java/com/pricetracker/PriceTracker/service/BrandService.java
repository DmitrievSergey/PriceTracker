package com.pricetracker.PriceTracker.service;

import com.pricetracker.PriceTracker.entity.Brand;
import com.pricetracker.PriceTracker.exception.BrandAlreadyExistsException;
import com.pricetracker.PriceTracker.exception.EntityNotFoundException;
import com.pricetracker.PriceTracker.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional
    public Brand createBrand(String name) throws BrandAlreadyExistsException {
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
}
