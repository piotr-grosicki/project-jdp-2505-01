package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProductRepository productRepository;

    private final Group group = Group.builder()
            .name("Test Group")
            .description("This is a test group")
            .createdAt(LocalDateTime.now())
            .build();

    @Test
    void testCreateGroup() {
        // Given & When
        Group savedGroup = groupRepository.save(group);

        // Then
        try {
            assertNotNull(savedGroup.getId());
            assertEquals("Test Group", savedGroup.getName());
            assertEquals("This is a test group", savedGroup.getDescription());
        } catch (Exception e) {
            fail("Group creation failed: " + e.getMessage());
        } finally {
            groupRepository.delete(savedGroup);
        }
    }

    @Test
    void testFindGroupById() {
        // Given
        Group savedGroup = groupRepository.save(group);

        // When
        Group foundGroup = groupRepository.findById(savedGroup.getId()).orElse(null);

        // Then
        try {
            assertNotNull(foundGroup);
            assertEquals(savedGroup.getId(), foundGroup.getId());
            assertEquals(savedGroup.getName(), foundGroup.getName());
            assertEquals(savedGroup.getDescription(), foundGroup.getDescription());
        } catch (Exception e) {
            fail("Group retrieval failed: " + e.getMessage());
        } finally {
            groupRepository.delete(savedGroup);
        }
    }

    @Test
    void testUpdateGroup() {
        // Given
        Group savedGroup = groupRepository.save(group);
        savedGroup.setName("Updated Group");
        savedGroup.setDescription("This is an updated test group");

        // When
        Group updatedGroup = groupRepository.save(savedGroup);

        // Then
        try {
            assertNotNull(updatedGroup);
            assertEquals("Updated Group", updatedGroup.getName());
            assertEquals("This is an updated test group", updatedGroup.getDescription());
        } catch (Exception e) {
            fail("Group update failed: " + e.getMessage());
        } finally {
            groupRepository.delete(updatedGroup);
        }
    }

    @Test
    void testAddProductToGroup() {
        // Given
        Group savedGroup = groupRepository.save(group);
        Product product = Product.builder()
                .name("Test Product")
                .price(new BigDecimal("19.99"))
                .description("This is a test product")
                .stockQuantity(100L)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .group(savedGroup)
                .build();

        savedGroup.getProducts().add(product);

        // When
        Product savedProduct = productRepository.save(product);

        // Then
        try {
            assertNotNull(savedProduct.getId());
            assertEquals(savedGroup.getId(), savedProduct.getGroup().getId());
            assertEquals("Test Product", savedProduct.getName());
        } catch (Exception e) {
            fail("Adding product to group failed: " + e.getMessage());
        } finally {
            groupRepository.delete(savedGroup);
            productRepository.delete(savedProduct);
        }
    }

    @Test
    void testRemoveProductFromGroup() {
        // Given
        Group savedGroup = groupRepository.save(group);
        Product product = Product.builder()
                .name("Test Product")
                .price(new BigDecimal("19.99"))
                .description("This is a test product")
                .stockQuantity(100L)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .group(savedGroup)
                .build();

        savedGroup.getProducts().add(product);

        Product savedProduct = productRepository.save(product);

        // When
        productRepository.delete(savedProduct);
        Group updatedGroup = groupRepository.findById(savedGroup.getId()).orElse(null);

        // Then
        try {
            assertNotNull(updatedGroup);
            assertFalse(updatedGroup.getProducts().contains(savedProduct));
        } catch (Exception e) {
            fail("Removing product from group failed: " + e.getMessage());
        } finally {
            groupRepository.delete(savedGroup);
        }
    }
}