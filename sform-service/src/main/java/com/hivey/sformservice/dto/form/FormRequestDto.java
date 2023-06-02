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

    /**
     * 5.1 설문 참여하기(=설문 응답하기)
     * - Client -> Controller -> Service
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FormAnswerReq {
        List<MultipleChoiceAnswerReq> multipleChoiceAnswers;
        List<ShortAnswerReq> shortAnswerResponses;

        @Builder
        public FormAnswerReq(List<MultipleChoiceAnswerReq> multipleChoiceAnswers, List<ShortAnswerReq> shortAnswerResponses) {
            this.multipleChoiceAnswers = multipleChoiceAnswers;
            this.shortAnswerResponses = shortAnswerResponses;
        }
    }

    /**
     * 5.1 설문 참여하기(=설문 응답하기)
     * - 객관식 질문에 대한 응답
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MultipleChoiceAnswerReq {
        private Long questionId;
        private List<Long> optionIds;

        @Builder
        public MultipleChoiceAnswerReq(Long questionId, List<Long> optionIds) {
            this.questionId = questionId;
            this.optionIds = optionIds;
        }
    }

    /**
     * 5.1 설문 참여하기(=설문 응답하기)
     * - 주관식 질문에 대한 응답
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ShortAnswerReq {
        private Long questionId;
        private String answer;

        @Builder
        public ShortAnswerReq(Long questionId, String answer) {
            this.questionId = questionId;
            this.answer = answer;
        }
    }




}
