package com.hivey.userservice.controller;

import com.hivey.userservice.service.UserService;
import com.hivey.userservice.service.UserServiceImpl;
import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserResponseDto.UserRes;
import com.hivey.userservice.global.config.BaseResponse;
import com.hivey.userservice.global.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final Environment env;


    @GetMapping("/health_check")
    public String status() {

        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time")

        );
    }

    /**
     * 회원가입
     */
    @PostMapping("/auth")
    public BaseResponse<UserRes> register(@RequestBody UserRegisterRequestDto user){
        log.debug("user: {}", user.toString());
        UserRes registerUser = userService.register(user);
        return new BaseResponse<>(registerUser);
    }



    /**
     * 1.2 사용자 정보 수정하기
     */
//    @PatchMapping("/{userId}")
//    public BaseResponse<String> userUpdate(@PathVariable Long userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) throws BaseException {
//
//        validateUserId(userId);
//        validateUserJwt(jwtService, userId);
//
//        if(userService.updateUser(userId, userUpdateRequestDto) == 1){
//            return new BaseResponse<>("사용자 정보 수정에 성공하였습니다.");
//        }else{
//            return new BaseResponse<>(FAILED_TO_UPDATE_USER);
//        }
//    }
}
