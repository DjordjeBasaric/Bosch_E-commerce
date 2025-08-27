package com.e_commerce.bosch.repositories;

import com.e_commerce.bosch.entities.CartItem;
import com.e_commerce.bosch.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductId(Long id, Long productId);

    boolean deleteByCartIdAndProductId(Long id, Long productId);
}
