package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.exception.CartNotFoundByIdException;
import com.kodilla.ecommercee.exception.ProductNotFoundByIdException;
import com.kodilla.ecommercee.exception.ProductNotInCartException;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateEmptyCart() {
        // Given
        Cart savedCart = Cart.builder()
                .id(1L)
                .status(CartStatusEnum.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
        when(cartRepository.save(any(Cart.class))).thenReturn(savedCart);

        // When
        Cart result = cartService.createEmptyCart();

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(CartStatusEnum.CREATED, result.getStatus());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void shouldGetCartContents() throws CartNotFoundByIdException {
        // Given
        List<Product> products = new ArrayList<>();
        Cart cart = Cart.builder()
                .products(products)
                .build();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        // When
        List<Product> result = cartService.getCartContents(1L);

        // Then
        assertSame(products, result);
    }

    @Test
    void getCartContentsShouldThrowException() {
        // Given
        when(cartRepository.findById(2L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(CartNotFoundByIdException.class, () -> cartService.getCartContents(2L));
    }

    @Test
    void shouldAddProductToCart() throws CartNotFoundByIdException, ProductNotFoundByIdException {
        // Given
        Cart cart = Cart.builder().id(1L).products(new ArrayList<>()).build();
        Product product = new Product();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));
        when(cartRepository.save(cart)).thenReturn(cart);

        // When
        Cart result = cartService.addProductToCart(1L, 5L);

        // Then
        assertTrue(result.getProducts().contains(product));
        verify(cartRepository).save(cart);
    }

    @Test
    void addProductToCartShouldThrowCartNotFound() {
        when(cartRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundByIdException.class, () -> cartService.addProductToCart(3L, 1L));
    }

    @Test
    void addProductToCartShouldThrowProductNotFound() {
        // Given
        Cart cart = Cart.builder().id(1L).products(new ArrayList<>()).build();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(9L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ProductNotFoundByIdException.class, () -> cartService.addProductToCart(1L, 9L));
    }

    @Test
    void shouldRemoveProductFromCart() throws CartNotFoundByIdException, ProductNotFoundByIdException, ProductNotInCartException {
        // Given
        Product product = new Product();
        List<Product> products = new ArrayList<>(); products.add(product);
        Cart cart = Cart.builder().id(1L).products(products).build();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(7L)).thenReturn(Optional.of(product));
        when(cartRepository.save(cart)).thenReturn(cart);

        // When
        Cart result = cartService.removeProductFromCart(1L, 7L);

        // Then
        assertFalse(result.getProducts().contains(product));
        verify(cartRepository).save(cart);
    }

    @Test
    void removeProductFromCartShouldThrowCartNotFound() {
        when(cartRepository.findById(4L)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundByIdException.class, () -> cartService.removeProductFromCart(4L, 1L));
    }

    @Test
    void removeProductFromCartShouldThrowProductNotFound() {
        // Given
        Cart cart = Cart.builder().id(1L).products(new ArrayList<>()).build();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(8L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ProductNotFoundByIdException.class, () -> cartService.removeProductFromCart(1L, 8L));
    }

    @Test
    void removeProductFromCartShouldThrowProductNotInCart() {
        // Given
        Product product = new Product();
        Cart cart = Cart.builder().id(1L).products(new ArrayList<>()).build();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(productRepository.findById(6L)).thenReturn(Optional.of(product));

        // When / Then
        assertThrows(ProductNotInCartException.class, () -> cartService.removeProductFromCart(1L, 6L));
    }

    @Disabled
    @Test
    void shouldConvertCartToOrder() throws CartNotFoundByIdException {
        // Given
        Cart cart = Cart.builder().id(2L).user(new User()).build();
        when(cartRepository.findById(2L)).thenReturn(Optional.of(cart));

        // When
        Order order = cartService.convertCartToOrder(2L);

        // Then
        assertEquals(cart, order.getCart());
        assertEquals(cart.getUser(), order.getUser());
        assertEquals(OrderStatusEnum.NEW, order.getStatus());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    void convertCartToOrderShouldThrowCartNotFound() {
        when(cartRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundByIdException.class, () -> cartService.convertCartToOrder(99L));
    }
}
