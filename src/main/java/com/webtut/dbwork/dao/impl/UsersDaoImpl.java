package com.webtut.dbwork.dao.impl;

import com.webtut.dbwork.dao.UsersDao;
import com.webtut.dbwork.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UsersDaoImpl implements UsersDao {

    private final JdbcTemplate jdbcTemplate;

    public UsersDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        jdbcTemplate.update("INSERT INTO users (user_id, login, password) values (default, ?, ?)",
                 user.getLogin(), user.getPassword());
    }

    @Override
    public Optional<User> findOne(long user_id) {
        List<User> results = jdbcTemplate.query("SELECT user_id, login, password FROM users WHERE user_id = ? LIMIT 1", new UserRowMapper(), user_id);
        return results.stream().findFirst();
    }

    @Override
    public List<User> find() {
        return jdbcTemplate.query("SELECT user_id, login, password FROM users", new UserRowMapper());

    }

    @Override
    public void update(long id, User user) {
        jdbcTemplate.update(
                "UPDATE users SET login = ? WHERE user_id = ?",
                 "second-punch", 1L
        );
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(
                "DELETE FROM users where user_id = ?",
                id
        );
    }

    public static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .userId(rs.getLong("user_id"))
                    .login(rs.getString("login"))
                    .password(rs.getString("password"))
                    .build();
        }
    }
}