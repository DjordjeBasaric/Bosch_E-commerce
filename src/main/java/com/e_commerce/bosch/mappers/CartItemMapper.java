package com.e_commerce.bosch.mappers;


import com.e_commerce.bosch.dtos.CartItemDTO;
import com.e_commerce.bosch.entities.CartItem;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartItemMapper {

    public static CartItemDTO cartItemToCartItemDTO(CartItem cartItem) {
        return CartItemDTO.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .product(ProductMapper.productEntitytoProductDto(cartItem.getProduct()))
                .build();
    }
}
