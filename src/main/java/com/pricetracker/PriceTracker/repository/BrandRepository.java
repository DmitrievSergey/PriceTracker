package com.pricetracker.PriceTracker.repository;

import com.pricetracker.PriceTracker.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    boolean existsByName(String name);
    Optional<Brand> findById(UUID brandId);
}
