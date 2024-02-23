package com.thatsgoodmoney.selectelhackbe.security;

import com.thatsgoodmoney.selectelhackbe.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserDto userDto;
}