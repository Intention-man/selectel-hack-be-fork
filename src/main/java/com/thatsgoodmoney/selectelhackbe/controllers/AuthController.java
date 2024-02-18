package com.thatsgoodmoney.selectelhackbe.controllers;


import com.thatsgoodmoney.selectelhackbe.domain.dto.UserDto;
import com.thatsgoodmoney.selectelhackbe.security.JwtResponse;
import com.thatsgoodmoney.selectelhackbe.services.UserService;
import com.thatsgoodmoney.selectelhackbe.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserDto userDto) {
        final Optional<UserDto> optionalUser = userService.findByLogin(userDto.getLogin());
        HttpStatus authStatus = authService.tryAuth(userDto, optionalUser);
        if (authStatus == HttpStatus.OK && optionalUser.isPresent()) {
            String token = authService.addTokenForUser(optionalUser.get());
            return new ResponseEntity<>(new JwtResponse(token), authStatus);
        }
        return new ResponseEntity<>(authStatus);
    }

    @PostMapping("/registration")
    public ResponseEntity<JwtResponse> registration(@RequestBody UserDto userDto) {
        if (userService.isUserExists(userDto))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        if (!userService.isLoginAndPasswordValid(userDto))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        userService.save(userDto);
        String token = authService.addTokenForUser(userDto);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.CREATED);
    }
}
