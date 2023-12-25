package com.webtut.dbwork.services.impl;

import com.webtut.dbwork.config.MapperConfig;
import com.webtut.dbwork.domain.dto.UserDto;
import com.webtut.dbwork.security.JwtProvider;
import com.webtut.dbwork.services.UserService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    // token - userId
    @Getter
    private final Map<String, Long> tokenStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public HttpStatus tryAuth(@NonNull UserDto userDto) {
        final Optional<UserDto> optionalUser = userService.findByLogin(userDto.getLogin());
        if (optionalUser.isEmpty())
            return HttpStatus.NOT_FOUND;

        if (MapperConfig.encoder().matches(userDto.getPassword(), optionalUser.get().getPassword()))
            return HttpStatus.OK;

        return HttpStatus.FORBIDDEN;
    }

    public String addToken(UserDto foundUser){
        final String accessToken = jwtProvider.generateAccessToken(foundUser);
        tokenStorage.put(accessToken, foundUser.getUserId());
        return accessToken;
    }

    public Long userIdFromToken(String rawToken) {
        if (tokenStorage.containsKey(rawToken))
            return tokenStorage.get(rawToken);

        return -1L;
    }
}