package com.webtut.dbwork.services;

import com.webtut.dbwork.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    UserEntity save(UserEntity userEntity);

    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long userId);

    boolean isExists(Long userId);
    boolean isExists(UserEntity userEntity);

    UserEntity partialUpdate(Long userId, UserEntity userEntity);

    void delete(Long userId);
}
