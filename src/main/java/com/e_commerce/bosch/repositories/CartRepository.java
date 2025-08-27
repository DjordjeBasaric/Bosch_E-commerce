package com.e_commerce.bosch.repositories;

import com.e_commerce.bosch.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {


    Optional<Cart> findByUserId(long userId);

    @Query("""
       select c from Cart c
       left join fetch c.items i
       left join fetch i.product
       where c.userId = :userId
       """)
    Optional<Cart> findByUserIdFetchAll(@Param("userId") Long userId);


}
