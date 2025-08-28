package com.e_commerce.bosch.controllers;

import com.e_commerce.bosch.dtos.AddToCartRequestDTO;
import com.e_commerce.bosch.dtos.CartDTO;
import com.e_commerce.bosch.dtos.UpdateItemInCartDTO;
import com.e_commerce.bosch.sequrity.JwtPrincipal;
import com.e_commerce.bosch.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addItemToCart(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @RequestBody AddToCartRequestDTO addToCartRequestDTO) {

        cartService.addItemToCart(addToCartRequestDTO.getProductId(), jwtPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<CartDTO> getAllProducts(@AuthenticationPrincipal JwtPrincipal jwtPrincipal) {
        return ResponseEntity.ok(cartService.getCartItemsByUserId(jwtPrincipal.getUserId()));
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<Void> updateItem(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long id,
            @RequestBody UpdateItemInCartDTO updateItemInCartDTO) {

        cartService.updateCartItemQuantity(
                jwtPrincipal.getUserId(),
                id,
                updateItemInCartDTO.getQuantity());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> deleteItem(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @PathVariable Long id) {

        cartService.deleteCartItem(jwtPrincipal.getUserId(), id);
        return ResponseEntity.ok().build();
    }
}
