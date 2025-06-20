package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.CartAlreadyHasAnOrderException;
import com.kodilla.ecommercee.exception.CartNotFoundByIdException;
import com.kodilla.ecommercee.exception.ProductNotFoundByIdException;
import com.kodilla.ecommercee.exception.ProductNotInCartException;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Cart createEmptyCart(User user) {
        Cart cart = Cart.builder()
                .status(CartStatusEnum.CREATED)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        return cartRepository.save(cart);
    }

    public List<Product> getCartContents(Long cartId) throws CartNotFoundByIdException {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundByIdException(cartId));
        return existingCart.getProducts();
    }

    public Cart addProductToCart(Long cartId, Long productId)
            throws CartNotFoundByIdException, ProductNotFoundByIdException {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundByIdException(cartId));
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundByIdException(productId));
        existingCart.getProducts().add(existingProduct);
        return cartRepository.save(existingCart);
    }

    public Cart removeProductFromCart(Long cartId, Long productId)
            throws CartNotFoundByIdException, ProductNotFoundByIdException, ProductNotInCartException {
        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundByIdException(cartId));
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundByIdException(productId));
        Optional.of(existingCart.getProducts())
                .filter(products -> products.remove(existingProduct))
                .orElseThrow(() -> new ProductNotInCartException(productId));
        return cartRepository.save(existingCart);
    }

    public Order convertCartToOrder(Long cartId)
            throws CartNotFoundByIdException, CartAlreadyHasAnOrderException {

        Cart existingCart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundByIdException(cartId));


        if (existingCart.getOrder() != null) {
            throw new CartAlreadyHasAnOrderException(cartId.toString());
        }

        BigDecimal totalPrice = existingCart.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order newOrder = Order.builder()
                .cart(existingCart)
                .user(existingCart.getUser())
                .status(OrderStatusEnum.NEW)
                .totalAmount(totalPrice)
                .createdAt(LocalDateTime.now())
                .products(new ArrayList<>(existingCart.getProducts()))
                .build();

        existingCart.setOrder(newOrder);

        return orderRepository.save(newOrder);
    }

}
