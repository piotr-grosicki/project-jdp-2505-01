package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartStatusEnum;
import com.kodilla.ecommercee.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart createEmptyCart() {
        Cart cart = Cart.builder()
                .status(CartStatusEnum.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
        return cartRepository.save(cart);
    }

    public String getCartContents(Long cartId) {
        // Logic to retrieve cart contents
        return "Cart Content: " + cartId;
    }

    public String addProductToCart(Long cartId, Long productId) {
        // Logic to add a product to the cart
        return "Product added to cart: " + productId;
    }

    public void removeProductFromCart(Long cartId, Long productId) {
        // Logic to remove a product from the cart
    }

    public String convertCartToOrder(Long cartId) {
        // Logic to convert the cart to an order
        return "Created order: " + cartId;
    }

}
