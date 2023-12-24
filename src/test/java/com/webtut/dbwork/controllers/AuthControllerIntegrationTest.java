package com.webtut.dbwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtut.dbwork.TestDataUtil;
import com.webtut.dbwork.domain.entities.UserEntity;
import com.webtut.dbwork.services.UserService;
import com.webtut.dbwork.services.impl.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {
    private final AuthService authService;
    private final UserService userService;

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthControllerIntegrationTest(AuthService authService, UserService userService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authService = authService;
        this.userService = userService;
    }

    @Test
    void testSuccessfullyRegistered() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        testUser.setUserId(null);
        String userJson = objectMapper.writeValueAsString(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    void testTooShortRegistrationData() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUsers().get(1);
        testUser.setUserId(null);
        String userJson = objectMapper.writeValueAsString(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    void testLoginIsOccupied() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        testUser.setUserId(null);
        String userJson = objectMapper.writeValueAsString(testUser);

        userService.save(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isConflict()
        );
    }

    @Test
    void testCorrectLoginResponses() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        testUser.setUserId(null);
        String userJson = objectMapper.writeValueAsString(testUser);
        userService.save(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.accessToken").isString()
        );

        testUser = TestDataUtil.createTestUser();
        testUser.setPassword("incorrect-password");
        userJson = objectMapper.writeValueAsString(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );

        testUser = TestDataUtil.createTestUser();
        testUser.setLogin("not-existing-login");
        userJson = objectMapper.writeValueAsString(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
