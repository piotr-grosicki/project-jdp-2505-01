package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartStatusEnum;
import com.kodilla.ecommercee.dto.CartDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CartMapper {

    public CartDTO mapToCartDto(Cart cart) {
        String status = cart.getStatus() != null ? cart.getStatus().name() : null;
        String createdAt = cart.getCreatedAt() != null ? cart.getCreatedAt().toString() : null;
        return new CartDTO(cart.getId(), status, createdAt);
    }

    public Cart mapToCart(CartDTO cartDto) {
        return Cart.builder()
                .status(cartDto.status() != null ? CartStatusEnum.valueOf(cartDto.status()) : null)
                .createdAt(cartDto.createdAt() != null ? LocalDateTime.parse(cartDto.createdAt()) : null)
                .build();
    }
}
