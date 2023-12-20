package com.webtut.dbwork.services.impl;

import com.webtut.dbwork.config.MapperConfig;
import com.webtut.dbwork.domain.dto.UserDto;
import com.webtut.dbwork.security.JwtProvider;
import com.webtut.dbwork.security.JwtResponse;
import com.webtut.dbwork.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final Map<String, String> tokenStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public ResponseEntity<JwtResponse> authAndCreateToken(@NonNull UserDto userDto) {
        final Optional<UserDto> optionalUser = userService.findByLogin(userDto.getLogin());
        if (optionalUser.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserDto foundUser = optionalUser.get();
        if (MapperConfig.encoder().matches(userDto.getPassword(), foundUser.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(foundUser);
            tokenStorage.put(accessToken, foundUser.getLogin());
            return new ResponseEntity<>(new JwtResponse(accessToken), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public boolean isTokenExists(String token) {
        return tokenStorage.containsKey(token);
    }
}