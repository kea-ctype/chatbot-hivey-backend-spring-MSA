package com.hivey.sformservice.dto.form;

import lombok.*;

import java.util.Date;
import java.util.List;

public class FormResponseDto {

    /**
     * 3.1 설문지 생성
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterRes {
        private Long formId;
    }

    /**
     * 설문지 생성 : All
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterFormRes {
        private Long formId;
        private String title;
        private String content;
        private Date startDate;
        private Date endDate;
        private char isAnonymous;
        private char isMandatory;
        private List<RegisterGroupRes> groups;
        private List<RegisterQuestionRes> questions;

        @Builder
        public RegisterFormRes(Long formId, String title, String content, Date startDate, Date endDate, char isAnonymous, char isMandatory, List<RegisterGroupRes> groups, List<RegisterQuestionRes> questions) {
            this.formId = formId;
            this.title = title;
            this.content = content;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isAnonymous = isAnonymous;
            this.isMandatory = isMandatory;
            this.groups = groups;
            this.questions = questions;
        }
    }
    // 선택 참여
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterGroupRes {
        private Long groupId;
        private String groupName;

        @Builder
        public RegisterGroupRes(Long groupId, String groupName) {
            this.groupId = groupId;
            this.groupName = groupName;
        }
    }



    // 설문 질문
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterQuestionRes {
        private Long questionId;
        private char type;
        private String title;
        private String content;
        private List<RegisterOptionRes> options;

        @Builder
        public RegisterQuestionRes(Long questionId, char type, String title, String content, List<RegisterOptionRes> options) {
            this.questionId = questionId;
            this.type = type;
            this.title = title;
            this.content = content;
            this.options = options;
        }
    }

    // 설문 옵션
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterOptionRes {
        private Long optionId;
        private String option;

        @Builder
        public RegisterOptionRes(Long optionId, String option) {
            this.optionId = optionId;
            this.option = option;
        }
    }

    /**
     * 스페이스에 해당하는 설문 목록을 불러올 때 사용하는 DTO
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FormListResponseDto {
        private Long formId;
        private String creator;
        private String title;
        private Date startDate;
        private Date endDate;


        @Builder
        public FormListResponseDto(Long formId, String creator, String title, Date startDate, Date endDate) {
            this.formId = formId;
            this.creator = creator;
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }


    /**
     * 특정 설문의 필수 여부 정보
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FormMandatoryOrNotRes {
        private Long formId;
        private String title;
        private char isMandatory;

        @Builder
        public FormMandatoryOrNotRes(Long formId, String title, char isMandatory) {
            this.formId = formId;
            this.title = title;
            this.isMandatory = isMandatory;
        }
    }


}
