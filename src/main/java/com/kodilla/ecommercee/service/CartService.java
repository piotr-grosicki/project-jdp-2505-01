package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exceptions.CartNotFoundException;
import com.kodilla.ecommercee.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.exceptions.ProductNotInCartException;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart createEmptyCart() {
        Cart cart = Cart.builder()
                .status(CartStatusEnum.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
        return cartRepository.save(cart);
    }

    public List<Product> getCartContents(Long cartId) throws CartNotFoundException {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(CartNotFoundException::new);
        return existingCart.getProducts();
    }

    public Cart addProductToCart(Long cartId, Long productId)
            throws CartNotFoundException, ProductNotFoundException {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(CartNotFoundException::new);
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        existingCart.getProducts().add(existingProduct);
        return cartRepository.save(existingCart);
    }

    public Cart removeProductFromCart(Long cartId, Long productId)
            throws CartNotFoundException, ProductNotFoundException, ProductNotInCartException {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(CartNotFoundException::new);
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        Optional.of(existingCart.getProducts())
                .filter(products -> products.remove(existingProduct))
                .orElseThrow(ProductNotInCartException::new);
        return cartRepository.save(existingCart);
    }

    public Order convertCartToOrder(Long cartId) throws CartNotFoundException {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(CartNotFoundException::new);
        return Order.builder()
                .cart(existingCart)
                .user(existingCart.getUser())
                .status(OrderStatusEnum.NEW)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
