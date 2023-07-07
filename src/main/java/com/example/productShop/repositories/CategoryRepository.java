package com.example.productShop.repositories;


import com.example.productShop.entities.Category;
import com.example.productShop.entities.dtos.category.CategoryStatisticsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM categories
            ORDER BY RAND ()
            LIMIT 2
            """)
    Optional<Set<Category>> getRandomEntities();


    @Query("""
            SELECT NEW com.example.productShop.entities.dtos
            .category.CategoryStatisticsDTO(c.name, COUNT(p.id), AVG(p.price), SUM(p.price)) FROM Product p
            JOIN p.categories AS c
            GROUP BY c.id
            ORDER BY COUNT(p.id) DESC
            """)
    List<CategoryStatisticsDTO> findAllCategoriesWithStatistics();

}