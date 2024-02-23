package com.thatsgoodmoney.selectelhackbe.mappers.impl;


import com.thatsgoodmoney.selectelhackbe.domain.dto.LoginDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.UserDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl {
    private final ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public UserDto entityToUserDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserEntity userDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    public LoginDto userDtoToLoginDto(UserDto userDto) {
        return LoginDto.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
