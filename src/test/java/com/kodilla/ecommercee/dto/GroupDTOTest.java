package com.kodilla.ecommercee.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GroupDTOTest {

    @Test
    void shouldCreateRecordAndGetValues() {
        // Given
        Long id = 42L;
        String name = "TestGroup";
        String description = "Desc";
        LocalDateTime now = LocalDateTime.of(2025, 6, 9, 15, 30);
        List<Long> products = Arrays.asList(1L, 2L, 3L);

        // When
        GroupDTO dto = new GroupDTO(id, name, description, now, products);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.name()).isEqualTo(name);
        assertThat(dto.description()).isEqualTo(description);
        assertThat(dto.createdAt()).isEqualTo(now);
        assertThat(dto.productsIds()).containsExactlyElementsOf(products);
    }

    @Test
    void equalsAndHashCodeShouldBeBasedOnAllFields() {
        // Given
        LocalDateTime time = LocalDateTime.of(2025, 6, 9, 16, 0);
        List<Long> prods = List.of(10L, 20L);

        GroupDTO dto1 = new GroupDTO(1L, "A", "D", time, prods);
        GroupDTO dto2 = new GroupDTO(1L, "A", "D", time, prods);
        GroupDTO dto3 = new GroupDTO(2L, "B", "D2", time, prods);

        // Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());

        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
    }

    @Test
    void toStringShouldContainAllFieldValues() {
        // Given
        LocalDateTime time = LocalDateTime.of(2025, 6, 9, 17, 45);
        List<Long> prods = List.of(99L);

        GroupDTO dto = new GroupDTO(7L, "GroupX", "DescX", time, prods);

        String ts = dto.toString();

        // Then
        assertThat(ts).contains("7");
        assertThat(ts).contains("GroupX");
        assertThat(ts).contains("DescX");
        assertThat(ts).contains(time.toString());
        assertThat(ts).contains("99");
    }
}
