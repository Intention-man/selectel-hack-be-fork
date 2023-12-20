package com.webtut.dbwork.controllers;


import com.webtut.dbwork.domain.dto.UserDto;
import com.webtut.dbwork.security.AuthService;
import com.webtut.dbwork.security.JwtResponse;
import com.webtut.dbwork.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserDto userDto) {
        return authService.authAndCreateToken(userDto);
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDto userDto) {
        if (userService.isLoginOccupied(userDto.getLogin())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            userService.save(userDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
