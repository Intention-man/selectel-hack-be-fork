package com.thatsgoodmoney.selectelhackbe.services.impl;

import com.thatsgoodmoney.selectelhackbe.security.JwtProvider;
import com.thatsgoodmoney.selectelhackbe.config.MapperConfig;
import com.thatsgoodmoney.selectelhackbe.domain.dto.UserDto;
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
    @Getter
    private final Map<String, Long> tokenStorage = new HashMap<>(); // token - userId
    private final JwtProvider jwtProvider;

    public HttpStatus tryAuth(@NonNull UserDto userCredentials, Optional<UserDto> optionalUser) {

        if (optionalUser.isEmpty())
            return HttpStatus.NOT_FOUND;

        if (MapperConfig.encoder().matches(userCredentials.getPassword(), optionalUser.get().getPassword()))
            return HttpStatus.OK;

        return HttpStatus.FORBIDDEN;
    }

    public String addTokenForUser(UserDto foundUser){
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