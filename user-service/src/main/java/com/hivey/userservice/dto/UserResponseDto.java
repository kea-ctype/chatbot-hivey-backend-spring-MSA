package com.hivey.userservice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserResponseDto {

    @Data
    @Builder
    public static class UserRes {
        private Long userIdx;
        private String name;

        public UserRes(Long userIdx, String name) {
            this.userIdx = userIdx;
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
    public static class UserLogoutResponseDto{
        private String connection;

        @Builder
        public UserLogoutResponseDto(String connection){this.connection=connection;}
    }
}
