package com.e_commerce.bosch.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequestDTO {
    Long productId;
    Long userId;
}
