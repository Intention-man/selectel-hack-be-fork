package com.webtut.dbwork.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private static final String TYPE = "Bearer";
    private String accessToken;
}