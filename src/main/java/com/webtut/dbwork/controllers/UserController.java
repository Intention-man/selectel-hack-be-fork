package com.webtut.dbwork.controllers;

import com.webtut.dbwork.domain.dto.UserDto;
import com.webtut.dbwork.domain.entities.UserEntity;
import com.webtut.dbwork.mappers.Mapper;
import com.webtut.dbwork.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;

    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public List<UserDto> listUsers(){
        List<UserEntity> users = userService.findAll();
        return users.stream().map(userMapper::mapTo).toList();
    }

    @GetMapping(path = "/users/{user_id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("user_id") Long userId){
        Optional<UserEntity> foundUser = userService.findById(userId);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/users/{user_id}")
    public ResponseEntity<UserDto> fullUpdateUser(
            @PathVariable("user_id") Long userId,
            @RequestBody UserDto userDto){
        if (userService.isExists(userId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDto.setUserId(userId);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/users/{user_id}")
    public ResponseEntity<UserDto> partialUpdateUser(
            @PathVariable("user_id") Long userId,
            @RequestBody UserDto userDto){
        if (userService.isExists(userId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDto.setUserId(userId);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.partialUpdate(userId, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{user_id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("user_id") Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}