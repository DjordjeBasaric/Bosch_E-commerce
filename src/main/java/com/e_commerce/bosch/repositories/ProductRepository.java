package com.e_commerce.bosch.repositories;

import com.e_commerce.bosch.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
           SELECT p FROM Product p
           WHERE (:search IS NULL OR LOWER(p.name) LIKE (CONCAT('%', :search, '%'))
                                  OR LOWER(p.description) LIKE (CONCAT('%',:search, '%')))
             AND (:priceMin IS NULL OR p.price >= :priceMin)
             AND (:priceMax IS NULL OR p.price <= :priceMax)
           """)
    Page<Product> findAllFilteredAndSorted(@Param("search") String search,
                                           @Param("priceMin") BigDecimal priceMin,
                                           @Param("priceMax") BigDecimal priceMax,
                                           Pageable pageable);
}
