package com.e_commerce.bosch.repositories;

import com.e_commerce.bosch.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(long userId);
}
