package com.e_commerce.bosch.services;


import com.e_commerce.bosch.dtos.CartDTO;
import com.e_commerce.bosch.dtos.CartItemDTO;
import com.e_commerce.bosch.entities.Cart;
import com.e_commerce.bosch.entities.CartItem;
import com.e_commerce.bosch.entities.Product;
import com.e_commerce.bosch.exceptions.ApiExceptionFactory;
import com.e_commerce.bosch.mappers.CartMapper;
import com.e_commerce.bosch.repositories.CartItemRepository;
import com.e_commerce.bosch.repositories.CartRepository;
import com.e_commerce.bosch.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Void addItemToCart(Long userId, Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(ApiExceptionFactory::productNotFound);

        Cart cart = findOrCreateCart(userId);

        CartItem cartItem = findOrCreateCartItem(cart, product.getPrice(), product);

        cartItem.setQuantity(cartItem.getQuantity()+1);

        cartItemRepository.save(cartItem);

        return null;
    }


    private Cart findOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                    .orElseGet(() -> cartRepository.save(
                            Cart.builder()
                                    .userId(userId)
                                    .items(new ArrayList<>())
                                    .build()));
    }

    private CartItem findOrCreateCartItem(Cart cart, BigDecimal productPrice, Product product) {
        return cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                    .orElseGet(() -> cartItemRepository.save(
                            CartItem.builder()
                                    .cart(cart)
                                    .quantity(0)
                                    .unitPrice(productPrice)
                                    .product(product)
                                    .build()));

    }

    @Transactional
    public Void updateCartItemQuantity(Long userId, Long productId, Integer quantity) {

        if (quantity < 1) throw ApiExceptionFactory.invalidQuantity();

        Product product = productRepository.findById(productId).orElseThrow(ApiExceptionFactory::productNotFound);

        Cart cart = findOrCreateCart(userId);

        CartItem cartItem = findOrCreateCartItem(cart, product.getPrice(), product);

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return null;
    }

    @Transactional
    public CartDTO getCartItemsByUserId(Long userId) {
        Cart cart = cartRepository.findByUserIdFetchAll(userId)
                                    .orElseThrow(ApiExceptionFactory::cartNotFound);
        return CartMapper.cartEntityToCartDTO(cart);
    }

    @Transactional
    public Void deleteCartItem(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(ApiExceptionFactory::cartNotFound);

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(ApiExceptionFactory::cartItemNotFound);

        cartItemRepository.delete(cartItem);

        return null;
    }


}
