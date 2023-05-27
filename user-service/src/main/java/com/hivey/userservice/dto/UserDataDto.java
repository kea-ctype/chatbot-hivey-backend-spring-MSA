package com.hivey.userservice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 데이터를 저장하기 위한 용도로만 사용되는 DTO
 * - Request용 X
 * - Response용 X
 */
public class UserDataDto {

    /**
     * 보여지는 사용자 정보만을 담고 있는 DTO
     *
     * @see ctype.survey.hivey.domain.user.mapper.UserMapper
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserNameDto {
        Long userId;
        String name;

        @Builder
        public UserNameDto(Long userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    }
}
