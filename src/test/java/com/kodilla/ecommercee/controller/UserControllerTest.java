package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.UserDTO;
import com.kodilla.ecommercee.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @Test
    void testGetAllUsers() throws Exception {
        UserDTO.UserDto userDto = new UserDTO.UserDto(
                1L, "John", "Doe", "hash", "john@doe.com",
                false, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userService.getAllUsers()).thenReturn(List.of(userDto));

        mockMvc.perform(get("/api/users").with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    void testGetUserById() throws Exception {
        UserDTO.UserDto userDto = new UserDTO.UserDto(
                1L, "John", "Doe", "hash", "john@doe.com",
                false, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userService.getUserById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/api/users/1").with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        UserDTO.UserDto userDto = new UserDTO.UserDto(
                1L, "John", "Doe", "hash", "john@doe.com",
                false, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userService.getUserByEmail("john@doe.com")).thenReturn(userDto);

        mockMvc.perform(get("/api/users/email").param("email", "john@doe.com").with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john@doe.com")));
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO.UserDto inputDto = new UserDTO.UserDto(
                null, "John", "Doe", "hash", "john@doe.com",
                false, null, null, null, null, List.of(), List.of());

        UserDTO.UserDto outputDto = new UserDTO.UserDto(
                1L, "John", "Doe", "hash", "john@doe.com",
                false, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userService.createUser(any(UserDTO.UserDto.class))).thenReturn(outputDto);

        mockMvc.perform(post("/api/users")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "passwordHash": "hash",
                            "email": "john@doe.com"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void testUpdateUser() throws Exception {
        UserDTO.UserDto userDto = new UserDTO.UserDto(
                1L, "John", "Updated", "newHash", "john@updated.com",
                true, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userService.updateUser(anyLong(), any(UserDTO.UserDto.class))).thenReturn(userDto);

        mockMvc.perform(put("/api/users/1")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "firstName": "John",
                            "lastName": "Updated",
                            "passwordHash": "newHash",
                            "email": "john@updated.com",
                            "blocked": true
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Updated")));
    }

    @Test
    void testBlockUser() throws Exception {
        UserDTO.UserDto userDto = new UserDTO.UserDto(
                1L, "John", "Doe", "hash", "john@doe.com",
                true, LocalDateTime.now(), null, null, null, List.of(), List.of());

        when(userService.blockUser(1L, true)).thenReturn(userDto);

        mockMvc.perform(patch("/api/users/1/block").param("isBlocked", "true").with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.blocked", is(true)));
    }

    @Test
    void testGenerateToken() throws Exception {
        UserDTO.Token token = new UserDTO.Token(
                1L, "generated-token", LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        when(userService.generateToken(1L)).thenReturn(token);

        mockMvc.perform(post("/api/users/1/token").with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1").with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNoContent());
    }
}
