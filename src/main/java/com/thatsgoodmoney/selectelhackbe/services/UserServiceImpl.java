package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.config.MapperConfig;
import com.thatsgoodmoney.selectelhackbe.domain.dto.UserDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.UserEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.UserMapperImpl;
import com.thatsgoodmoney.selectelhackbe.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;

    public UserDto save(UserDto userDto) {
        UserEntity userEntity = userMapper.userDtoToEntity(userDto);
        userEntity.setPassword(MapperConfig.encoder().encode(userEntity.getPassword()));
        return userMapper.entityToUserDto(userRepository.save(userEntity));
    }

    public Optional<UserDto> findById(Long userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        return optionalUserEntity.map(userMapper::entityToUserDto);
    }

    public Optional<UserDto> findByLogin(String login) {
        Optional<UserEntity> optionalUser = userRepository.findByLogin(login);
        return optionalUser.map(userMapper::entityToUserDto);
    }

    public boolean isExists(Long userId) {
        return userRepository.existsById(userId);
    }

    public boolean isUserExists(UserDto userDto) {
        UserEntity userEntity = userMapper.userDtoToEntity(userDto);
        Optional<UserEntity> optionalUser = userRepository.findByLogin(userEntity.getEmail());
        if (optionalUser.isPresent()) {
            UserEntity foundUser = optionalUser.get();
            return MapperConfig.encoder().matches(userEntity.getPassword(), foundUser.getPassword());
        }
        return false;
    }

    public List<UserDto> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(userMapper::entityToUserDto).toList();
    }

    public UserDto partialUpdate(Long userId, UserDto userDto) {
        userDto.setUserId(userId);
        return userRepository.findById(userId).map(existingUser -> {
            Optional.ofNullable(userDto.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(MapperConfig.encoder().encode(userDto.getPassword())).ifPresent(existingUser::setPassword);
            Optional.ofNullable(userDto.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(userDto.getTag()).ifPresent(existingUser::setTag);
            Optional.ofNullable(userDto.getCity()).ifPresent(existingUser::setCity);
            Optional.ofNullable(userDto.getBloodType()).ifPresent(existingUser::setBloodType);
            return userMapper.entityToUserDto(userRepository.save(existingUser));
        }).orElseThrow(() -> new RuntimeException("User doesn't exists"));
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean isLoginAndPasswordValid(UserDto userDto) {
        return userDto.getEmail().length() >= 6 && userDto.getPassword().length() >= 6;
    }
}
