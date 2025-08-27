package com.e_commerce.bosch.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    Long id;
    ProductDTO product;
    BigDecimal unitPrice;
    Integer quantity;
}
