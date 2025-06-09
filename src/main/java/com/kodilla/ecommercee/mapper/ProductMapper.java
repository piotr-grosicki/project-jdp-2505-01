package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductMapper {

    public ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.isActive(),
                product.getCreatedAt());
    }

    public List<ProductDTO> mapToProductDTOList(List<Product> products) {
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.isActive(),
                        product.getCreatedAt()))
                .toList();
    }

    public Product toProduct(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.id())
                .name(productDTO.name())
                .description(productDTO.description())
                .price(productDTO.price())
                .stockQuantity(productDTO.quantity())
                .isActive(productDTO.isActive())
                .build();
    }
}
