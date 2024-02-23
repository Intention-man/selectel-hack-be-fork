package com.thatsgoodmoney.selectelhackbe.services.impl;

import com.thatsgoodmoney.selectelhackbe.config.MapperConfig;
import com.thatsgoodmoney.selectelhackbe.domain.dto.UserDto;
import com.thatsgoodmoney.selectelhackbe.security.JwtDecoder;
import com.thatsgoodmoney.selectelhackbe.security.JwtProvider;
import io.jsonwebtoken.Claims;
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
    private final JwtDecoder jwtDecoder;

    public HttpStatus tryAuth(@NonNull UserDto userCredentials, Optional<UserDto> optionalUser) {

        if (optionalUser.isEmpty())
            return HttpStatus.NOT_FOUND;

        if (MapperConfig.encoder().matches(userCredentials.getPassword(), optionalUser.get().getPassword()))
            return HttpStatus.OK;

        return HttpStatus.FORBIDDEN;
    }

    public String addTokenForUser(UserDto foundUser){
        final String accessToken = jwtProvider.generateAccessToken(foundUser);
        tokenStorage.put(accessToken, foundUser.getId());
        return accessToken;
    }

    public Long userIdFromStorage(String rawToken) {
        if (tokenStorage.containsKey(rawToken)) {
            return tokenStorage.get(rawToken);
        }
        return -1L;
    }

    public Long userIdFromToken(String rawToken) {
        Claims claims = jwtDecoder.decodeToken(rawToken);
        if (claims != null) return claims.get("userId", Long.class);
        return null; // if token expired
    }
}