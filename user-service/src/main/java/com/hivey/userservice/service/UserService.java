package com.hivey.userservice.service;


import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserResponseDto.UserRes;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserRes register(UserRegisterRequestDto userRegisterRequestDto);
}
