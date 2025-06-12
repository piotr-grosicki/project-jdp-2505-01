package com.kodilla.ecommercee.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserDTOTest {

    @Test
    void shouldReturnPassedValues() {
        // given
        LocalDateTime now = LocalDateTime.now();
        UserDTO dto = new UserDTO(
                1L,
                "Anna",
                "Nowak",
                "anna@nowak.pl",
                false,
                now,
                "tokenXYZ",
                now,
                now.plusDays(1)
        );

        // then
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.firstName()).isEqualTo("Anna");
        assertThat(dto.email()).isEqualTo("anna@nowak.pl");
        assertThat(dto.isBlocked()).isFalse();
        assertThat(dto.tokenExpiresAt()).isAfter(now);
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        // given
        LocalDateTime time = LocalDateTime.now();
        UserDTO dto1 = new UserDTO(
                2L, "Jan", "Kowalski", "jan@kowalski.pl",
                false, time, null, null, null
        );
        UserDTO dto2 = new UserDTO(
                2L, "Jan", "Kowalski", "jan@kowalski.pl",
                false, time, null, null, null
        );

        // then
        assertThat(dto1).isEqualTo(dto2)
                .hasSameHashCodeAs(dto2);
    }

    @Test
    void toStringShouldContainFieldNames() {
        // given
        UserDTO dto = new UserDTO(
                3L, "Ola", "Malinowska", "ola@mal.pl",
                true, LocalDateTime.now(), null, null, null
        );

        // expect
        String toString = dto.toString();

        assertThat(toString).contains("id=3")
                .contains("firstName=Ola")
                .contains("isBlocked=true");
    }
}
