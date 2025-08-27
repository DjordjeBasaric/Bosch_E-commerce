package com.e_commerce.bosch.mappers;

import com.e_commerce.bosch.dtos.ProductDTO;
import com.e_commerce.bosch.entities.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public static ProductDTO productEntitytoProductDto(Product p) {
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .description(p.getDescription())
                .build();
    }
}
