package com.hivey.userservice.application;


import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserResponseDto.GetUserRes;
import com.hivey.userservice.dto.UserResponseDto.UserLoginRes;
import com.hivey.userservice.dto.UserResponseDto.UserRes;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserRes register(UserRegisterRequestDto userRegisterRequestDto);

    UserLoginRes getUserDetailsByEmail(String userName);

    GetUserRes getUserByUserId(Long userId);
}
