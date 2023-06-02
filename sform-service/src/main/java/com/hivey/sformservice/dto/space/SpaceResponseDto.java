package com.hivey.sformservice.dto.space;

import com.hivey.sformservice.dto.form.FormResponseDto;
import com.hivey.sformservice.dto.form.FormResponseDto.FormListResponseDto;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto.SpaceGroupListRes;
import lombok.*;

import java.util.List;

public class SpaceResponseDto {

    /**
     * 3.1 스페이스 생성하기
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceCreateRes {
        private Long spaceId;
        private String accessCode;

        @Builder
        public SpaceCreateRes(Long spaceId, String accessCode) {
            this.spaceId = spaceId;
            this.accessCode = accessCode;
        }
    }

    /**
     * 3.4 참여한 스페이스 목록 불러오기
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceListRes {
        private Long spaceId;
        private String name;
        private String img;
        private int isManager;

        @Builder
        public SpaceListRes(Long spaceId, String name, String img, int isManager) {
            this.spaceId = spaceId;
            this.name = name;
            this.img = img;
            this.isManager = isManager;
        }
    }

    /**
     * 3.5 스페이스 조회하기
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceInfoRes {

        // Mandatory
        private String name;
        private String img;
        private List<FormListResponseDto> forms;

        // Optional (관리자)
        private Integer memberCount = null;
        private List<SpaceGroupListRes> groups = null;

        @Builder

        public SpaceInfoRes(String name, String img, List<FormListResponseDto> forms, Integer memberCount, List<SpaceGroupListRes> groups) {
            this.name = name;
            this.img = img;
            this.forms = forms;
            this.memberCount = memberCount;
            this.groups = groups;
        }
    }

}
