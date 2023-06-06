package com.hivey.userservice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class UserResponseDto {

    @Data
    @Builder
    public static class UserRes implements Serializable {
        private Long userId;
        private String name;
        private String email;
        private String img;

        public UserRes(Long userId, String name, String email, String img) {
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.img = img;
        }

        public UserRes(Long userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserLoginRes implements Serializable {
        private Long userId;
        private String name;
        private String email;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserRes implements Serializable {
        private Long userId;
        private String img;
        private String email;
        private String name;

    }

}
