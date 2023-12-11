package com.webtut.dbwork.repositories;

import com.webtut.dbwork.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT user from UserEntity user where user.login = ?1")
    Optional<UserEntity> existsByLogin(String reqLogin);
}
