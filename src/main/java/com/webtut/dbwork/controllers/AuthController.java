package com.webtut.dbwork.controllers;


import com.webtut.dbwork.domain.dto.UserDto;
import com.webtut.dbwork.security.JwtResponse;
import com.webtut.dbwork.services.UserService;
import com.webtut.dbwork.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @PostMapping("/registration")
    public ResponseEntity<HttpStatusCode> registration(@RequestBody UserDto userDto) {
        if (userService.isUserExists(userDto))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        if (userDto.getLogin().length() < 6 || userDto.getPassword().length() < 6)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        userService.save(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
