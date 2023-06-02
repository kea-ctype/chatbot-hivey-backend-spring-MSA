package com.hivey.sformservice.dto.form;

import lombok.*;

import java.util.Date;
import java.util.List;

public class FormRequestDto {

    /**
     * 설문지 생성하기 : All
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterFormReq {
        private String title;
        private String content;
        private Date startDate;
        private Date endDate;
        private char isAnonymous;
        private char isMandatory;
        private List<RegisterGroupReq> groups;
        private List<RegisterQuestionReq> questionRequests;

        @Builder
        public RegisterFormReq(String title, String content, Date startDate, Date endDate, char isAnonymous, char isMandatory, List<RegisterGroupReq> groups, List<RegisterQuestionReq> questionRequests) {
            this.title = title;
            this.content = content;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isAnonymous = isAnonymous;
            this.isMandatory = isMandatory;
            this.groups = groups;
            this.questionRequests = questionRequests;
        }
    }

    // 선택 참여
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterGroupReq {
        private Long groupId;
    }

    //설문 질문 & 옵션
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegisterQuestionReq {
        private char type;
        private String title;
        private String content;
        private List<String> options;

        @Builder
        public RegisterQuestionReq(char type, String title, String content, List<String> options) {
            this.type = type;
            this.title = title;
            this.content = content;
            this.options = options;
        }
    }


}
