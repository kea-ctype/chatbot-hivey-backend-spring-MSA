package com.hivey.userservice.api;

import com.hivey.userservice.application.UserService;
import com.hivey.userservice.dto.UserRequestDto.UserLoginRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserLogoutRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserUpdateRequestDto;
import com.hivey.userservice.dto.UserResponseDto;
import com.hivey.userservice.dto.UserResponseDto.UserRes;
import com.hivey.userservice.global.config.BaseException;
import com.hivey.userservice.global.config.BaseResponse;
import com.hivey.userservice.global.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.hivey.userservice.global.config.BaseResponseStatus.FAILED_TO_UPDATE_USER;
import static com.hivey.userservice.global.config.BaseResponseStatus.NOT_EXISTS_USER;
import static com.hivey.userservice.global.util.ValidationUtil.validateUserId;
import static com.hivey.userservice.global.util.ValidationUtil.validateUserJwt;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }

    /**
     * 회원가입
     */
    @PostMapping("/register")
    public BaseResponse<UserRes> register(@RequestBody UserRegisterRequestDto user){
        log.debug("user: {}", user.toString());
        UserRes registerUser = userService.register(user);
        return new BaseResponse<>(registerUser);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public BaseResponse<UserRes> login(@RequestBody UserLoginRequestDto user){
        UserRes loginUser = userService.login(user);
        log.debug("loginUser: {}", loginUser);

        if(loginUser.getUserIdx() < 0) {
            return new BaseResponse<>(NOT_EXISTS_USER);
        }

        return new BaseResponse<>(loginUser);
    }

    /**
     *
     * 2.7 로그아웃
     */
    @PostMapping("/logout/{userId}")
    public BaseResponse<UserResponseDto.UserLogoutResponseDto> logout(@PathVariable Long userId,@RequestBody UserLogoutRequestDto userLogoutRequestDto) throws BaseException {
        validateUserId(userId);
        validateUserJwt(jwtService, userId);
        return new BaseResponse<>(userService.logout(userId,userLogoutRequestDto));
    }

    /**
     * 1.2 사용자 정보 수정하기
     */
    @PatchMapping("/{userId}")
    public BaseResponse<String> userUpdate(@PathVariable Long userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) throws BaseException {

        validateUserId(userId);
        validateUserJwt(jwtService, userId);

        if(userService.updateUser(userId, userUpdateRequestDto) == 1){
            return new BaseResponse<>("사용자 정보 수정에 성공하였습니다.");
        }else{
            return new BaseResponse<>(FAILED_TO_UPDATE_USER);
        }
    }
}
