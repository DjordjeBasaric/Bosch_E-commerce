package com.e_commerce.bosch.mappers;

import com.e_commerce.bosch.dtos.CartDTO;
import com.e_commerce.bosch.dtos.CartItemDTO;
import com.e_commerce.bosch.entities.Cart;
import com.e_commerce.bosch.entities.CartItem;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CartMapper {

    public static CartDTO cartEntityToCartDTO(Cart cart) {

        List<CartItemDTO> cartItemDTOS = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            cartItemDTOS.add(CartItemMapper.cartItemToCartItemDTO(cartItem));
        }

        return CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(cartItemDTOS)
                .build();
    }
}
