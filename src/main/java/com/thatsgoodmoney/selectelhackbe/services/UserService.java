package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.entities.UserEntity;
import com.thatsgoodmoney.selectelhackbe.domain.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    UserEntity save(UserEntity userEntity);
    UserDto save(UserDto userDto);

    List<UserDto> findAll();

    Optional<UserDto> findById(Long userId);
    Optional<UserDto> findByLogin(String login);

    boolean isExists(Long userId);
    boolean isLoginOccupied(String login);
    boolean isUserExists(UserDto userDto);

    UserDto partialUpdate(Long userId, UserDto userDto);

    void delete(Long userId);

    boolean isLoginAndPasswordValid(UserDto userDto);
}
