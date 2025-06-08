package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductMapper {

    public ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().toString(),
                product.getStockQuantity().toString(),
                product.isActive() ? "ACTIVE" : "INACTIVE",
                product.getCreatedAt().toString());
    }

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

    public Product toProduct(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.id())
                .name(productDTO.name())
                .description(productDTO.description())
                .price(new BigDecimal(productDTO.price()))
                .stockQuantity(Long.parseLong(productDTO.quantity()))
                .isActive("ACTIVE".equalsIgnoreCase(productDTO.status()))
                .build();
    }
}
