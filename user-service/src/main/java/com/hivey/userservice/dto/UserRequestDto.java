package com.hivey.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hivey.userservice.domain.AuthPassword;
import com.hivey.userservice.domain.User;
import com.hivey.userservice.mapper.UserMapper;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserRequestDto {

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserLoginRequestDto {
        @NotNull(message = "Email cannot be null")
        @Size(min = 2, message = "Email not be less than two characters")
        @Email
        private String email;

        @NotNull(message = "Password cannot be null")
        @Size(min = 8, message = "Password must be equals or greater than 8 characters")
        private String password;
    }



    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserRegisterRequestDto {
        private String name;
        private String email;
        private String password;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserInfoDto {
        private String email;
        private String password;
        private char status;

        // DTO -> 엔티티로 변환
        public User toEntity() {
            return User.builder()
                    .email(email)
                    .status('Y')
                    .build();
        }

//        public AuthPassword toUserEntity() {
//            return AuthPassword.builder()
//                    .user(toEntity())
//                    .password(password)
//                    .build();
//        }

        // DTO 생성
        @Builder
        public UserInfoDto(String email) {
            this.email = email;
            status = 'Y';
        }
    }

    //logout
    //@data에 getter 와 setter 포함된다길래 data 사용 아직 구분이 어려움
    // 음 entitiy 수정해야할것 같은데 겁난다
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserLogoutRequestDto{
        Long userId;
        String connection;
        @Builder
        public UserLogoutRequestDto(Long userId,String connection){
            this.userId=userId;//이렇게 하면 id 값도 삽입되는 건가?
            this.connection=connection;
        }

        //DTO -> Entity

    }


    //사용자 정보 수정

    @Getter
    public static class UserUpdateRequestDto{
        //Long userId;
        String name;
        String img;

        @Builder
        public UserUpdateRequestDto( String name, String img){
            this.name=name;
            this.img=img;

        }


    }



}