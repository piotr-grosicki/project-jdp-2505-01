package com.kodilla.ecommercee.controller.empty;

import com.kodilla.ecommercee.controller.UserController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    private static String exampleUserJson;

    @BeforeAll
    static void initTests() {
        System.out.println("Tests empty methods for GroupController");
        exampleUserJson = "{\"name\":\"John\",\"email\":\"john@example.com\"}";
    }

    @BeforeEach
    void beforeEachTest() {
        System.out.println("\n***Preparation before each empty controller test***");
    }

    @AfterEach
    void afterEachTest() {
        System.out.println("Test passed");
    }

    @Test
    void shouldCreateUser() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(exampleUserJson))
                .andExpect(status().isOk())
                .andExpect(content().string(exampleUserJson));
        System.out.println("Test confirm status ok and contain mocked user");
    }

    @Test
    void shouldBlockUser() throws Exception {

        String userId = "123";

        mockMvc.perform(put("/api/v1/users/block/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User blocked: " + userId));
        System.out.println("Test confirm status ok and contain content ");
    }

    @Test
    void shouldGenerateKeyForUser() throws Exception {

        String userId = "456";

        mockMvc.perform(post("/api/v1/users/" + userId + "/keys"))
                .andExpect(status().isOk())
                .andExpect(content().string("Key generated for user: " + userId));
        System.out.println("Test confirm status ok and contain content ");
    }
}