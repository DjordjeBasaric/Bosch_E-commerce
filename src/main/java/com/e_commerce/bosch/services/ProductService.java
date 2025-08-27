package com.e_commerce.bosch.services;

import com.e_commerce.bosch.dtos.ProductDTO;
import com.e_commerce.bosch.entities.Product;
import com.e_commerce.bosch.exceptions.ApiExceptionFactory;
import com.e_commerce.bosch.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(ApiExceptionFactory::productNotFound);

        return productEntityToProductDTO(product);
    }

    public Page<ProductDTO> getAllProducts(int page, int size, String sortBy,
                                           String direction, String search,
                                           BigDecimal priceMin, BigDecimal priceMax) {

        if (priceMin != null && priceMax != null && priceMin.compareTo(priceMax)>0){
            throw ApiExceptionFactory.minPriceGreaterThenMaxPrice();
        }

        Sort sort = getSort(sortBy, direction);

        Pageable pageable = PageRequest.of(page, size, sort);

        String searchNormalized = normalizeSearch(search);

        Page<Product> products = productRepository.findAllFilteredAndSorted(searchNormalized, priceMin, priceMax, pageable);

        return products.map(ProductService::productEntityToProductDTO);
    }

    private Sort getSort(String sortBy, String direction) {
        Set<String> allowedSortItems = Set.of("id", "name", "price");

        if(!allowedSortItems.contains(sortBy)){
            throw ApiExceptionFactory.sortByNotFound();
        }

        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(direction);
        }
        catch (IllegalArgumentException e){
            throw ApiExceptionFactory.sortDirectionNotFound();
        }

        return Sort.by(sortDirection, sortBy);
    }

    private String normalizeSearch(String search) {
        return (search==null || search.isBlank()) ? "" : search.strip().toLowerCase();
    }

    static ProductDTO productEntityToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

}
