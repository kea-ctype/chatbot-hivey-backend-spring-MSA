package com.hivey.sformservice.dto.space;

import lombok.*;

public class SpaceResponseDto {

    /**
     * 3.1 스페이스 생성하기
     */
    @Data
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
