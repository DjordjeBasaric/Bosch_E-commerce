package com.e_commerce.bosch.controllers;

import com.e_commerce.bosch.dtos.AddToCartRequestDTO;
import com.e_commerce.bosch.dtos.CartDTO;
import com.e_commerce.bosch.dtos.UpdateItemInCartDTO;
import com.e_commerce.bosch.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addItemToCart(@RequestBody AddToCartRequestDTO addToCartRequestDTO) {
        return ResponseEntity.ok(
                cartService.addItemToCart(addToCartRequestDTO.getProductId(), addToCartRequestDTO.getUserId()));
    }

    @GetMapping()
    public ResponseEntity<CartDTO> getAllProducts(@RequestBody Long userId) {
        return ResponseEntity.ok(cartService.getCartItemsByUserId(userId));
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<Void> updateItem(
            @PathVariable Long id,
            @RequestBody UpdateItemInCartDTO updateItemInCartDTO
    ) {
        return ResponseEntity.ok(
                cartService.updateCartItemQuantity(
                        updateItemInCartDTO.getUserId(),
                        id,
                        updateItemInCartDTO.getQuantity()));
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id, @RequestBody Long userId) {
        return ResponseEntity.ok(cartService.deleteCartItem(userId, id));
    }
}
