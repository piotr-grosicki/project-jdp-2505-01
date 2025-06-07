package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMapper {

    public List<ProductDTO> mapToProductDTOList(List<Product> products) {
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice().toString(),
                        product.getStockQuantity().toString(),
                        product.isActive() ? "ACTIVE" : "INACTIVE",
                        product.getCreatedAt().toString()))
                .toList();
    }
}
