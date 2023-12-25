package com.webtut.dbwork.services.impl;

import com.webtut.dbwork.config.MapperConfig;
import com.webtut.dbwork.domain.dto.UserDto;
import com.webtut.dbwork.domain.entities.UserEntity;
import com.webtut.dbwork.mappers.Mapper;
import com.webtut.dbwork.repositories.UserRepository;
import com.webtut.dbwork.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper<UserEntity, UserDto> userMapper;

    public UserServiceImpl(UserRepository userRepository, Mapper<UserEntity, UserDto> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        userEntity.setPassword(MapperConfig.encoder().encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        userEntity.setPassword(MapperConfig.encoder().encode(userEntity.getPassword()));
        return userMapper.mapTo(userRepository.save(userEntity));
    }

    @Override
    public Optional<UserDto> findById(Long userId) {
        Optional<UserEntity> optionalUserDto = userRepository.findById(userId);
        return optionalUserDto.map(userMapper::mapTo);
    }

    @Override
    public Optional<UserDto> findByLogin(String login) {
        Optional<UserEntity> optionalUser = userRepository.findByLogin(login);
        return optionalUser.map(userMapper::mapTo);
    }

    @Override
    public boolean isExists(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public boolean isLoginOccupied(String login) {
        Optional<UserEntity> optionalUser = userRepository.findByLogin(login);
        return optionalUser.isPresent();
    }

    @Override
    public boolean isUserExists(UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        Optional<UserEntity> optionalUser = userRepository.findByLogin(userEntity.getLogin());
        if (optionalUser.isPresent()) {
            UserEntity foundUser = optionalUser.get();
            return MapperConfig.encoder().matches(userEntity.getPassword(), foundUser.getPassword());
        }
        return false;
    }

    @Override
    public List<UserDto> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(userMapper::mapTo).toList();
    }

    @Override
    public UserDto partialUpdate(Long userId, UserDto userDto) {
        userDto.setUserId(userId);
        return userRepository.findById(userId).map(existingUser -> {
            Optional.ofNullable(userDto.getLogin()).ifPresent(existingUser::setLogin);
            Optional.ofNullable(userDto.getPassword()).ifPresent(existingUser::setPassword);
            return userMapper.mapTo(userRepository.save(existingUser));
        }).orElseThrow(() -> new RuntimeException("User doesn't exists"));
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean isLoginAndPasswordValid(UserDto userDto){
        return userDto.getLogin().length() >= 6 && userDto.getPassword().length() >= 6;
    }
}
