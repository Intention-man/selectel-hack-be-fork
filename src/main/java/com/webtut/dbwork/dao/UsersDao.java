package com.webtut.dbwork.dao;

import com.webtut.dbwork.domain.User;

import java.util.List;
import java.util.Optional;

public interface UsersDao {
    void create(User user);

    Optional<User> findOne(long l);

    List<User> find();

    void update(long id, User user);

    void delete(long id);
}
