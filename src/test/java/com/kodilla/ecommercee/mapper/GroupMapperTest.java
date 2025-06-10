package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.GroupDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GroupMapperTest {
    private GroupMapper groupMapper;

    @BeforeEach
    void setUp() {
        groupMapper = new GroupMapper();
    }

    @Test
    void shouldMapToGroupDTO() {
        // Given
        Product product1 = Product.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(new BigDecimal("100.0"))
                .build();

        Product product2 = Product.builder()
                .id(null)
                .name("Product 2")
                .description("Description 2")
                .price(new BigDecimal("200.0"))
                .build();

        LocalDateTime createdAt = LocalDateTime.of(2025, 6, 9, 12, 0);
        Group group = Group.builder()
                .id(10L)
                .name("Test Group")
                .description("A group for testing")
                .createdAt(createdAt)
                .products(Arrays.asList(product1, null, product2))
                .build();


        // When
        GroupDTO dto = groupMapper.mapToGroupDTO(group);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(10L);
        assertThat(dto.name()).isEqualTo("Test Group");
        assertThat(dto.description()).isEqualTo("A group for testing");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.productsIds()).containsExactly(1L);
    }

    @Test
    void shouldMapToGroupDTOList() {
        // Given
        Group group1 = Group.builder()
                .id(1L)
                .name("G1")
                .description("D1")
                .createdAt(LocalDateTime.now())
                .build();
        Group group2 = Group.builder()
                .id(2L)
                .name("G2")
                .description("D2")
                .createdAt(LocalDateTime.now())
                .build();
        List<Group> groups = Arrays.asList(group1, group2);

        // When
        List<GroupDTO> dtos = groupMapper.mapToGroupDTOList(groups);

        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos).extracting(GroupDTO::id).containsExactly(1L, 2L);
    }

    @Test
    void shouldMapToGroup() {
        // Given
        LocalDateTime createdAt = LocalDateTime.of(2025, 6, 9, 12, 0);
        GroupDTO dto = new GroupDTO(
                20L,
                "DTO Group",
                "DTO Description",
                createdAt,
                Collections.singletonList(99L)
        );

        // When
        Group group = groupMapper.mapToGroup(dto);

        // Then
        assertThat(group).isNotNull();
        assertThat(group.getId()).isEqualTo(20L);
        assertThat(group.getName()).isEqualTo("DTO Group");
        assertThat(group.getDescription()).isEqualTo("DTO Description");
        assertThat(group.getCreatedAt()).isEqualTo(createdAt);
        assertThat(group.getProducts()).isNull();
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts() {
        // Given
        Group group = Group.builder()
                .id(5L)
                .name("NoProd")
                .description("No products here")
                .createdAt(LocalDateTime.now())
                .products(Collections.emptyList())
                .build();

        // When
        GroupDTO dto = groupMapper.mapToGroupDTO(group);

        // Then
        assertThat(dto.productsIds()).isEmpty();
    }
}
