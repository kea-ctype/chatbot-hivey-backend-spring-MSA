package com.hivey.sformservice.dto.form;

import lombok.*;

import java.io.Serializable;

public class FormDataDto {

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserRes implements Serializable {
        private Long userId;
        private String img;
        private String email;
        private String name;

    }

}
