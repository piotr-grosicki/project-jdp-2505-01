package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.CartDTO;
import com.kodilla.ecommercee.dto.OrderDTO;
import com.kodilla.ecommercee.dto.ProductDTO;
import com.kodilla.ecommercee.exception.CartNotFoundByIdException;
import com.kodilla.ecommercee.exception.ProductNotFoundByIdException;
import com.kodilla.ecommercee.exception.ProductNotInCartException;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;

    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<CartDTO> createEmptyCart() {
        CartDTO cartDto = cartMapper.mapToCartDto(cartService.createEmptyCart());
        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<ProductDTO>> getCartContents(@PathVariable Long cartId)
            throws CartNotFoundByIdException {
        List<Product> cartContents = cartService.getCartContents(cartId);
        return ResponseEntity.ok(productMapper.mapToProductDTOList(cartContents));
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartDTO> addProductToCart(
            @PathVariable Long cartId, @PathVariable Long productId)
            throws CartNotFoundByIdException, ProductNotFoundByIdException {
        Cart cart = cartService.addProductToCart(cartId, productId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartDTO> removeProductFromCart(
            @PathVariable Long cartId, @PathVariable Long productId)
            throws CartNotFoundByIdException, ProductNotFoundByIdException, ProductNotInCartException {
        Cart cart = cartService.removeProductFromCart(cartId, productId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @PostMapping("/{cartId}/orders")
    public ResponseEntity<OrderDTO> convertCartToOrder(@PathVariable Long cartId)
            throws CartNotFoundByIdException {
        Order order = cartService.convertCartToOrder(cartId);
        return ResponseEntity.ok(orderMapper.toDto(order));
    }
}