package com.webtut.dbwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtut.dbwork.TestDataUtil;
import com.webtut.dbwork.domain.entities.UserEntity;
import com.webtut.dbwork.services.UserService;
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
class UserControllerIntegrationTest {
    private final UserService userService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerIntegrationTest(UserService userService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Test
    void testThatCreateUserSuccessfullyReturnsHttp201Created() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        testUser.setUserId(null);
        String userJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    void testThatCreateUserSuccessfullyReturnsSavedUser() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        testUser.setUserId(null);
        String userJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.userId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.login").value(testUser.getLogin())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").isString()
        );
    }

    @Test
    void testThatListUsersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    void testThatListUsersReturnsListOfUsers() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        userService.save(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].userId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].login").value("cool-fighter")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].password").isString()
        );
    }

    @Test
    void testThatGetUserReturnsHttpStatus200WhenUserExists() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        userService.save(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + testUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    void testThatGetUserReturnsUserWhenUserExists() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        userService.save(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + testUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.userId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.login").value("cool-fighter")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").isString()
        );
    }

    @Test
    void testThatFullUpdateUserReturnsHttpStatus404WhenNoUserExists() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        UserEntity savedUser = userService.save(testUser);
        String userJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    void testThatFullUpdateUpdatesExistingUser() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        UserEntity savedUser = userService.save(testUser);

        UserEntity userEntity2 = TestDataUtil.createTestUsers().get(1);
        String userJson = objectMapper.writeValueAsString(userEntity2);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.userId").value(savedUser.getUserId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.login").value("second-breath")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("456")
        );
    }

    @Test
    void testThatDeleteUserReturnsHttpStatus204ForExistingUser() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        UserEntity savedUser = userService.save(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}