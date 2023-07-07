package com.example.productShop.repositories;
import com.example.productShop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE price BETWEEN :minRange AND :maxRange ORDER BY price", nativeQuery = true)
    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal minRange, BigDecimal maxRange);

    @Query(value = "SELECT * FROM categories ORDER BY RAND() LIMIT 2", nativeQuery = true)
    Optional<Set<Product>> getRandomCategories();
}