package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private UserMapper mapper;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        mapper = new UserMapper();
        now = LocalDateTime.now();
    }

    @Test
    void shouldMapUserToDto() {
        // given
        User user = User.builder()
                .id(1L)
                .firstName("Alicja")
                .lastName("Wójcik")
                .email("ali@wp.pl")
                .isBlocked(true)
                .createdAt(now)
                .token("xyz")
                .tokenCreatedAt(now)
                .tokenExpiresAt(now.plusHours(24))
                .build();

        // when
        UserDTO dto = mapper.toDto(user);

        // then
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.firstName()).isEqualTo("Alicja");
        assertThat(dto.lastName()).isEqualTo("Wójcik");
        assertThat(dto.email()).isEqualTo("ali@wp.pl");
        assertThat(dto.isBlocked()).isTrue();
        assertThat(dto.token()).isEqualTo("xyz");
    }

    @Test
    void shouldMapDtoToUser() {
        // given
        UserDTO dto = new UserDTO(
                5L, "Marek", "Zieliński", "marek@domain.com",
                false, now, "token123", now, now.plusDays(1)
        );

        // when
        User user = mapper.toEntity(dto);

        // then
        assertThat(user.getId()).isEqualTo(5L);
        assertThat(user.getFirstName()).isEqualTo("Marek");
        assertThat(user.getLastName()).isEqualTo("Zieliński");
        assertThat(user.getEmail()).isEqualTo("marek@domain.com");
        assertThat(user.isBlocked()).isFalse();
        assertThat(user.getToken()).isEqualTo("token123");
        assertThat(user.getTokenExpiresAt()).isEqualTo(dto.tokenExpiresAt());
    }

    @Test
    void shouldMapListOfUsersToListOfDtos() {
        // given
        User user1 = User.builder()
                .id(1L).firstName("Anna").lastName("Nowak").email("a@n.pl")
                .createdAt(now).build();
        User user2 = User.builder()
                .id(2L).firstName("Tomek").lastName("Lis").email("t@l.pl")
                .createdAt(now).build();

        List<User> userList = List.of(user1, user2);

        // when
        List<UserDTO> dtoList = mapper.mapToDtoList(userList);

        // then
        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).firstName()).isEqualTo("Anna");
        assertThat(dtoList.get(1).email()).isEqualTo("t@l.pl");
    }
}
