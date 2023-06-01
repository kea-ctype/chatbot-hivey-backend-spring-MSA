package com.hivey.userservice.api;

import com.hivey.userservice.application.UserService;
import com.hivey.userservice.application.UserServiceImpl;
import com.hivey.userservice.dto.UserRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserResponseDto;
import com.hivey.userservice.dto.UserResponseDto.GetUserRes;
import com.hivey.userservice.dto.UserResponseDto.UserRes;
import com.hivey.userservice.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
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
     *  사용자 정보 조회
     */
    @GetMapping("/users/{userId}")
    public BaseResponse<GetUserRes> getUser(@PathVariable Long userId) {
        GetUserRes getUserRes = userService.getUserByUserId(userId);
        return new BaseResponse<>(getUserRes);
    }
}
