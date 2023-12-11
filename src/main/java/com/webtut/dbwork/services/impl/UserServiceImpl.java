package com.webtut.dbwork.services.impl;

import com.webtut.dbwork.config.MapperConfig;
import com.webtut.dbwork.domain.entities.UserEntity;
import com.webtut.dbwork.repositories.UserRepository;
import com.webtut.dbwork.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperConfig mapperConfig;

    public UserServiceImpl(UserRepository userRepository, MapperConfig mapperConfig) {
        this.userRepository = userRepository;
        this.mapperConfig = mapperConfig;
    }


    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }


    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean isExists(Long userId) {
        return !userRepository.existsById(userId);
    }

    @Override
    public boolean isExists(UserEntity userEntity) {
        Optional<UserEntity> optionalUser = userRepository.existsByLogin(userEntity.getLogin());
        if (optionalUser.isPresent()){
            UserEntity foundUser = optionalUser.get();
            return mapperConfig.encoder().matches(userEntity.getPassword(), foundUser.getPassword());
        }
        return false;
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList();
    }
    @Override
    public UserEntity partialUpdate(Long userId, UserEntity userEntity) {
        userEntity.setUserId(userId);

        return userRepository.findById(userId).map(existingUser -> {
            Optional.ofNullable(userEntity.getLogin()).ifPresent(existingUser::setLogin);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User doesn't exists"));
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
