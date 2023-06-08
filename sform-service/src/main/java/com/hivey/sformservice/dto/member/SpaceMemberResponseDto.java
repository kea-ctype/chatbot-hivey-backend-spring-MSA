package com.hivey.sformservice.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SpaceMemberResponseDto {

    /**
     * 스페이스 그룹 멤버 목록을 불러올 때 사용한다.
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceMemberByGroupRes {
        private Long memberId;
        private String name;
        private String email;
        private char position;

        @Builder
        public SpaceMemberByGroupRes(Long memberId, String name, String email, char position) {
            this.memberId = memberId;
            this.name = name;
            this.email = email;
            this.position = position;
        }
    }

    /**
     * 4.1 스페이스 참여하기
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceMemberJoinRes {
        private Long spaceId;
        private Long memberId;

        @Builder
        public SpaceMemberJoinRes(Long spaceId, Long memberId) {
            this.spaceId = spaceId;
            this.memberId = memberId;
        }
    }
}
