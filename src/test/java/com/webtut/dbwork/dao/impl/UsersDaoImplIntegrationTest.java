package com.webtut.dbwork.dao.impl;

import com.webtut.dbwork.TestDataUtil;
import com.webtut.dbwork.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UsersDaoImplIntegrationTest {

    private final UsersDaoImpl underTest;

    @Autowired
    public UsersDaoImplIntegrationTest(UsersDaoImpl underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatUserCanBeCreatedAndRecalled(){
        User user = TestDataUtil.createTestUser();
        underTest.create(user);
        Optional<User> result = underTest.findOne(user.getUserId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void testThatMultipleUsersCanBeCreatedAndRecalled(){
        List<User> users = TestDataUtil.createTestUsers();
        users.forEach(underTest::create);

        List<User> result = underTest.find();
        assertThat(result).hasSize(3).containsExactlyElementsOf(users);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        User user = TestDataUtil.createTestUser();
        underTest.create(user);
        user.setLogin("second-punch");
        underTest.update(user.getUserId(), user);
        Optional<User> result = underTest.findOne(user.getUserId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        User authorA = TestDataUtil.createTestUser();
        underTest.create(authorA);
        underTest.delete(authorA.getUserId());
        Optional<User> result = underTest.findOne(authorA.getUserId());
        assertThat(result).isEmpty();
    }
}
