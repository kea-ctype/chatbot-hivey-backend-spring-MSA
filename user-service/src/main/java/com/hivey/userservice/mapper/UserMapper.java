package com.hivey.userservice.mapper;

import com.hivey.userservice.domain.AuthPassword;
import com.hivey.userservice.domain.User;
import com.hivey.userservice.dto.UserDataDto;
import com.hivey.userservice.dto.UserDataDto.UserNameDto;
import com.hivey.userservice.dto.UserRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserLoginRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserLogoutRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * DTO (UserLoginRequestDto) -> Entity
     */
    User toUserFromUserLoginRequestDto(UserLoginRequestDto userLoginRequestDto);

    /**
     * DTO (UserRegisterRequestDto) -> Entity
     */
    User toUserFromUserRegisterEmailRequestDto(UserRegisterRequestDto userRegisterRequestDto);

    @Mapping(source = "userRegisterRequestDto.password", target = "password")
    AuthPassword toAuthPassword(User user, UserRegisterRequestDto userRegisterRequestDto);

    /**
     * Entity (User) -> Data DTO (UserNameDto)
     */
    UserNameDto toUserNameDto(User user);

    /**
     * DTO (UserLogoutRequestDto) -> Entity
      */
    User toUserFromUserLogoutRequestDto(UserLogoutRequestDto userLogoutRequestDto);

    User toUserFromUserUpdateRequestDto(UserUpdateRequestDto userUpdateRequestDto);
}
