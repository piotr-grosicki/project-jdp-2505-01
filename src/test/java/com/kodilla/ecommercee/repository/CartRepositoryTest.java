package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    private Cart buildCart(CartStatusEnum status) {
        return Cart.builder()
                .status(status)
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
    }

    private Cart buildSecondCart(CartStatusEnum status) {
        return Cart.builder()
                .status(status)
                .createdAt(LocalDateTime.now().minusDays(5))
                .build();
    }


    @Test
    void testCreateNewCart() {
        // Given
        Cart cart = buildCart(CartStatusEnum.COMPLETED);

        // When
        Cart savedCart = cartRepository.save(cart);

        // Then
        try {
            assertNotNull(savedCart.getId());
            assertEquals(CartStatusEnum.COMPLETED, savedCart.getStatus());
        } catch (Exception e) {
            fail("Cart should be saved successfully, but an exception occurred: " + e.getMessage());
        } finally {
            cartRepository.delete(savedCart);
        }
    }

    @Test
    void testFindAllByStatus() {
        // Given
        Cart cart1 = buildCart(CartStatusEnum.COMPLETED);
        Cart cart2 = buildCart(CartStatusEnum.COMPLETED);
        Cart cart3 = buildCart(CartStatusEnum.CANCELLED);
        cartRepository.save(cart1);
        cartRepository.save(cart2);
        cartRepository.save(cart3);

        // When
        Optional<List<Cart>> cartsByCompletedStatus = cartRepository.findAllByStatus(CartStatusEnum.COMPLETED);

        // Then
        try {
            assertTrue(cartsByCompletedStatus.isPresent());
            assertEquals(2, cartsByCompletedStatus.get().size());
        } catch (Exception e) {
            fail("Carts should be found by status successfully, but an exception occurred: " + e.getMessage());
        } finally {
            cartRepository.delete(cart1);
            cartRepository.delete(cart2);
            cartRepository.delete(cart3);
        }
    }

    @Test
    void testFindByCreatedAtBefore() {
        // Given
        Cart newCart = buildCart(CartStatusEnum.ABANDONED);
        Cart oldCart = buildSecondCart(CartStatusEnum.ABANDONED);

        cartRepository.save(newCart);
        cartRepository.save(oldCart);

        // When
        Optional<Cart> oldCarts = cartRepository.findByCreatedAtBefore(LocalDateTime.now().minusDays(2));

        // Then
        try {
            assertTrue(oldCarts.isPresent());
            assertEquals(oldCart.getId(), oldCarts.get().getId());
        } catch (Exception e) {
            fail("Cart should be found by created date successfully, but an exception occurred: " + e.getMessage());
        } finally {
            cartRepository.delete(oldCart);
            cartRepository.delete(newCart);
        }
    }

    @Test
    void testDeleteCart() {
        // Given
        Cart cart = buildCart(CartStatusEnum.COMPLETED);
        Cart savedCart = cartRepository.save(cart);

        // When
        cartRepository.delete(savedCart);
        Cart foundCart = cartRepository.findById(savedCart.getId()).orElse(null);

        // Then
        assertNull(foundCart, "Cart should be deleted successfully");
    }
}