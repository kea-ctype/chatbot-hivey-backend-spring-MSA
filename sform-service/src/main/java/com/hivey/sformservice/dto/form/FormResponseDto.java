package com.hivey.sformservice.dto.form;

import lombok.*;

import java.util.Date;

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
