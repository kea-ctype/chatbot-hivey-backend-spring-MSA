package com.hivey.userservice.api;

import com.hivey.userservice.application.TokenBlacklistService;
import com.hivey.userservice.application.UserService;
import com.hivey.userservice.application.UserServiceImpl;
import com.hivey.userservice.dto.UserRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserResponseDto;
import com.hivey.userservice.dto.UserResponseDto.GetUserRes;
import com.hivey.userservice.dto.UserResponseDto.UserRes;
import com.hivey.userservice.global.config.BaseResponse;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final Environment env;
    private final TokenBlacklistService tokenBlacklistService;


    @GetMapping("/health_check")
    @Timed(value = "users.status", longTask = true)
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
    @Timed(value = "users.register", longTask = true)
    public BaseResponse<UserRes> register(@RequestBody UserRegisterRequestDto user){
        log.debug("user: {}", user.toString());
        UserRes registerUser = userService.register(user);
        return new BaseResponse<>(registerUser);
    }



    /**
     *  사용자 정보 조회
     */
    @GetMapping("/users/{userId}")
    @Timed(value = "users.userInfo", longTask = true)
    public GetUserRes getUser(@PathVariable Long userId) {
        GetUserRes getUserRes = userService.getUserByUserId(userId);
        return getUserRes;
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // JWT 추출
        String token = extractJwtToken(request);

        // JWT 유효성 검사
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid JWT token");
        }

        // JWT 블랙리스트 추가
        tokenBlacklistService.addToBlacklist(token);

        return ResponseEntity.ok("Logout successful");
    }

    private String extractJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더의 값이 null 또는 비어있을 경우
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return null;
        }

        // Authorization 헤더 값이 "Bearer {JWT}" 형식이 아닌 경우
        if (!authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        // JWT 토큰 추출
        return authorizationHeader.substring(7); // "Bearer " 부분을 제외하고 토큰만 반환
    }
}