package com.hivey.sformservice.dto.group;


import com.hivey.sformservice.dto.member.SpaceMemberResponseDto;
import com.hivey.sformservice.dto.member.SpaceMemberResponseDto.SpaceMemberByGroupRes;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class SpaceGroupResponseDto{

    /**
     * 스페이스 멤버 목록을 포함한 스페이스 그룹 목록을 반환할 때 사용한다.
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceGroupListRes {
        private String name;
        private List<SpaceMemberByGroupRes> members;

        @Builder
        public SpaceGroupListRes(String name, List<SpaceMemberByGroupRes> members) {
            this.name = name;
            this.members = members;
        }
    }

}
