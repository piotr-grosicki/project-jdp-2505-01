package com.kodilla.ecommercee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    // private CartRepository cartRepository;

    public void createEmptyCart() {
        // Logic to create an empty cart
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
