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
        private String jwtToken;

        public UserRes(Long userIdx, String name, String jwtToken) {
            this.userIdx = userIdx;
            this.name = name;
            this.jwtToken = jwtToken;
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserLogoutResponseDto{
        private String connection;

        @Builder
        public UserLogoutResponseDto(String connection){this.connection=connection;}
    }
}
