package com.pricetracker.PriceTracker.repository;

import com.pricetracker.PriceTracker.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @EntityGraph(attributePaths = {"brand"})
    List<Product> findAll();
}
