package com.hivey.sformservice.dto.space;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SpaceResponseDto {

    /**
     * 3.1 스페이스 생성하기
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceCreateResponseDto {
        private Long spaceId;
        private String accessCode;

        @Builder
        public SpaceCreateResponseDto(Long spaceId, String accessCode) {
            this.spaceId = spaceId;
            this.accessCode = accessCode;
        }
    }

}
