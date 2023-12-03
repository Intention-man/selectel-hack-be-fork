package com.webtut.dbwork.dao.impl;

import com.webtut.dbwork.TestDataUtil;
import com.webtut.dbwork.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UsersDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UsersDaoImpl underTest;

    @Test
    public void testThatCreateUserGeneratesCorrectSql(){
        User user = TestDataUtil.createTestUser();

        underTest.create(user);

        verify(jdbcTemplate).update(eq("INSERT INTO users (user_id, login, password) values (default, ?, ?)"),
                eq("Cool fighter"), eq("123"));
    }

    @Test
    public void testThatFindOneUserGeneratesTheCorrectSql(){
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT user_id, login, password FROM users WHERE user_id = ? LIMIT 1"),
                ArgumentMatchers.<UsersDaoImpl.UserRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindManyUsersGeneratesTheCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT user_id, login, password FROM users"),
                ArgumentMatchers.<UsersDaoImpl.UserRowMapper>any());
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        User user = TestDataUtil.createTestUser();
        underTest.update(1L, user);

        verify(jdbcTemplate).update(
                "UPDATE users SET login = ? WHERE user_id = ?",
                "second-punch", 1L
        );
    }

    @Test
    public void testThatDeleteGeneratesTheCorrectSql() {
        underTest.delete(1L);
        verify(jdbcTemplate).update(
                "DELETE FROM users where user_id = ?",
                1L
        );
    }
}
