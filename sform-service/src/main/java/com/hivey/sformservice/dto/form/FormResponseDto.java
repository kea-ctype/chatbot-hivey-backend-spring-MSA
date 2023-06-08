package com.hivey.sformservice.dto.form;

import com.hivey.sformservice.domain.question.MultipleChoiceOption;
import com.hivey.sformservice.dto.form.FormDataDto.GetUserRes;
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
        private String creator;
        private String formLink;
        private Date startDate;
        private Date endDate;
        private char isAnonymous;
        private char isMandatory;
        private List<RegisterGroupRes> groups;
        private List<RegisterQuestionRes> questions;

        @Builder
        public RegisterFormRes(Long formId, String title, String content, String creator, String formLink, Date startDate, Date endDate, char isAnonymous, char isMandatory, List<RegisterGroupRes> groups, List<RegisterQuestionRes> questions) {
            this.formId = formId;
            this.title = title;
            this.content = content;
            this.creator = creator;
            this.formLink = formLink;
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
     * 스페이스 설문 조회
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FormListBySpaceRes {
        private Long formId;
        private String title;
        private Date startDate;
        private Date endDate;
        private boolean checkJoin; //참여 여부

        @Builder
        public FormListBySpaceRes(Long formId, String title, Date startDate, Date endDate, boolean checkJoin) {
            this.formId = formId;
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.checkJoin = checkJoin;
        }
    }

    /**
     * 스페이스에 해당하는 설문 목록을 불러올 때 사용하는 DTO
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FormListResponseDto {
        private Long formId;
        private String title;
        private Date startDate;
        private Date endDate;
        private boolean isTarget;
        private boolean isSubmit;


        @Builder
        public FormListResponseDto(Long formId, String title, Date startDate, Date endDate, boolean isTarget, boolean isSubmit) {
            this.formId = formId;
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isTarget = isTarget;
            this.isSubmit = isSubmit;
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

    /**
     * 5.1 설문 참여하기(=설문 응답하기)
     * - 주관식 질문에 대한 응답
     */
    @Data
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

    /**
     * 특정 설문지 조회 - 질문 & 답변
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetFormRes {
        private Long formId;
        private String title;
        private String content;
        private String creator;
        private String formLink;
        private Date startDate;
        private Date endDate;
        private List<GetQuestionRes> questions;


        @Builder
        public GetFormRes(Long formId, String title, String content, String creator, String formLink, Date startDate, Date endDate, List<GetQuestionRes> questions) {
            this.formId = formId;
            this.title = title;
            this.content = content;
            this.creator = creator;
            this.formLink = formLink;
            this.startDate = startDate;
            this.endDate = endDate;
            this.questions = questions;
        }


    }

    // 설문 질문
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetQuestionRes {
        private Long questionId;
        private char type;
        private String title;
        private String content;
        private List<GetOptionRes> options;
        private GetAnswerRes answer;
        private List<GetAnswerOptionRes> answerOptions;

        @Builder
        public GetQuestionRes(Long questionId, char type, String title, String content, List<GetOptionRes> options, GetAnswerRes answer, List<GetAnswerOptionRes> answerOptions) {
            this.questionId = questionId;
            this.type = type;
            this.title = title;
            this.content = content;
            this.options = options;
            this.answer = answer;
            this.answerOptions = answerOptions;
        }
    }

    // 설문 옵션
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetOptionRes {
        private Long optionId;
        private String optionContent;

        @Builder
        public GetOptionRes(Long optionId, String optionContent) {
            this.optionId = optionId;
            this.optionContent = optionContent;
        }
    }

    // 설문 응답
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetAnswerRes {
        private Long answerId;
        private String answer;

        @Builder
        public GetAnswerRes(Long answerId, String answer) {
            this.answerId = answerId;
            this.answer = answer;
        }
    }

    // 설문 옵션 응답
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetAnswerOptionRes {
        private Long answerId;
        private Long optionId; //선택한 옵션

        @Builder
        public GetAnswerOptionRes(Long answerId, Long optionId) {
            this.answerId = answerId;
            this.optionId = optionId;
        }
    }

    /**
     * 설문 참여 현황 목록 불러올 때 사용하는 DTO
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FormGetSubmissionList{
        // 설문에 대한 정보
        private Long formId;
        private boolean isMandatory;

        // 멤버 정보
        private Long memberId;
        private GetUserRes getUserRes;
        private boolean isSubmit;

        // 만약 선택 설문인 경우 아래에 값을 넣어 보내준다.
        private Long groupId;

        @Builder
        public FormGetSubmissionList(Long formId, boolean isMandatory, Long memberId, GetUserRes getUserRes, boolean isSubmit, Long groupId) {
            this.formId = formId;
            this.isMandatory = isMandatory;
            this.memberId = memberId;
            this.getUserRes = getUserRes;
            this.isSubmit = isSubmit;
            this.groupId = groupId;
        }
    }

    /**
     * 특정 설문의 타겟 그룹
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TargetGroupsByFormRes {
        private Long formId;
        private Long groupId;
        private String groupName;

        @Builder
        public TargetGroupsByFormRes(Long formId, Long groupId, String groupName) {
            this.formId = formId;
            this.groupId = groupId;
            this.groupName = groupName;
        }
    }

    /**
     * 설문 결과
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetFormResultRes {
        private Long formId;
        private String title;
        private String content;
        private String creator;
        private String formLink;
        private Date startDate;
        private Date endDate;
        private char isAnonymous;
        private List<GetQuestionResult> getQuestionResults;

        @Builder
        public GetFormResultRes(Long formId, String title, String content, String creator, String formLink, Date startDate, Date endDate, char isAnonymous, List<GetQuestionResult> getQuestionResults) {
            this.formId = formId;
            this.title = title;
            this.content = content;
            this.creator = creator;
            this.formLink = formLink;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isAnonymous = isAnonymous;
            this.getQuestionResults = getQuestionResults;
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetQuestionResult {
        private Long questionId;
        private String title;
        private String content;
        private List<MultipleAnswerResult> multipleAnswerResults;
        private List<AnswerResult> answerResults;

        @Builder
        public GetQuestionResult(Long questionId, String title, String content, List<MultipleAnswerResult> multipleAnswerResults, List<AnswerResult> answerResults) {
            this.questionId = questionId;
            this.title = title;
            this.content = content;
            this.multipleAnswerResults = multipleAnswerResults;
            this.answerResults = answerResults;
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MultipleAnswerResult {
        private Long optionId;
        private String optionContent;
        private Long count;

        @Builder
        public MultipleAnswerResult(Long optionId, String optionContent, Long count) {
            this.optionId = optionId;
            this.optionContent = optionContent;
            this.count = count;
        }
    }
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AnswerResult {
        private Long answerId;
        private String name;
        private String answer;

        @Builder
        public AnswerResult(Long answerId, String name, String answer) {
            this.answerId = answerId;
            this.name = name;
            this.answer = answer;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SubmissionByUserResponseDto {
        private boolean isSubmit;

        @Builder
        public SubmissionByUserResponseDto(boolean isSubmit) {
            this.isSubmit = isSubmit;
        }
    }



}
