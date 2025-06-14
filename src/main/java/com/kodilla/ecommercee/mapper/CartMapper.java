package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartStatusEnum;
import com.kodilla.ecommercee.dto.CartDTO;
import com.kodilla.ecommercee.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final ProductMapper productMapper;

    public CartDTO mapToCartDto(Cart cart) {
        String status = cart.getStatus() != null ? cart.getStatus().name() : null;
        List<ProductDTO> products = productMapper.mapToProductDTOList(cart.getProducts());
        return new CartDTO(cart.getId(), status, cart.getCreatedAt(),
                getProductIdList(products),
                cart.getUser().getId(),
                cart.getOrder() != null ? cart.getOrder().getId() : null);
    }

    private List<Long> getProductIdList(List<ProductDTO> products) {
        return products.stream()
                .map(this::getId)
                .toList();
    }

    public Cart mapToCart(CartDTO cartDto) {
        return Cart.builder()
                .status(cartDto.status() != null ? CartStatusEnum.valueOf(cartDto.status()) : null)
                .createdAt(cartDto.createdAt())
                .build();
    }

    private Long getId(ProductDTO productDTO) {
        return productDTO != null ? productDTO.id() : null;
    }
}
