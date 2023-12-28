package com.webtut.dbwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtut.dbwork.TestDataUtil;
import com.webtut.dbwork.config.MapperConfig;
import com.webtut.dbwork.domain.dto.UserDto;
import com.webtut.dbwork.domain.entities.UserEntity;
import com.webtut.dbwork.mappers.Mapper;
import com.webtut.dbwork.services.UserService;
import com.webtut.dbwork.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class UserControllerIntegrationTest{
    private String token;
    private final UserService userService;
    private final AuthService authService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final Mapper<UserEntity, UserDto> userMapper;

    @BeforeAll
    void beforeAll() {
        UserEntity user = TestDataUtil.createTestUser();
        userService.save(user);
        token = "Bearer " + authService.addTokenForUser(userMapper.mapTo(user));
    }

    @Test
    void testThatListUsersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
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
                        .header("Authorization", token)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].userId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].login").value(testUser.getLogin())
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
                        .header("Authorization", token)
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
                        .header("Authorization", token)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.userId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.login").value(testUser.getLogin())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").isString()
        );
    }

    @Test
    void testThatFullUpdateUserReturnsHttpStatus200WhenUserExists() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        UserEntity savedUser = userService.save(testUser);
        String userJson = objectMapper.writeValueAsString(testUser);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    void testThatFullUpdateUpdatesExistingUser() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        userService.save(testUser);

        UserEntity userEntity2 = TestDataUtil.createTestUsers().get(1);
        String hashedPassword = MapperConfig.encoder().encode(testUser.getPassword());
        String userJson = objectMapper.writeValueAsString(userEntity2);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + testUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.userId").value(testUser.getUserId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.login").value(userEntity2.getLogin())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").isString()
        );
    }

    @Test
    void testThatDeleteUserReturnsHttpStatus204ForExistingUser() throws Exception {
        UserEntity testUser = TestDataUtil.createTestUser();
        UserEntity savedUser = userService.save(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}