package com.hivey.sformservice.dto.space;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class SpaceDataDto {

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserRes implements Serializable {
        private Long userId;
        private String img;
        private String name;

    }
}
