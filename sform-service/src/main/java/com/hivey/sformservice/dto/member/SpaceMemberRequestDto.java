package com.hivey.sformservice.dto.member;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class SpaceMemberRequestDto {

    /**
     * 4.1 스페이스 참여하기
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceMemberJoinReq {

        @NotNull(message = "스페이스 접속 코드를 입력해 주세요.")
        private String accessCode;
    }
}
