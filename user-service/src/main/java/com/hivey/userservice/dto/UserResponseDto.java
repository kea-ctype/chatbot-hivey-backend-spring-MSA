package com.hivey.userservice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserResponseDto {

    @Data
    @Builder
    public static class UserRes {
        private Long userId;
        private String name;

        public UserRes(Long userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserLoginRes {
        private Long userId;
        private String name;
        private String email;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserRes {
        private Long userId;
        private String img;
        private String name;

    }

}
