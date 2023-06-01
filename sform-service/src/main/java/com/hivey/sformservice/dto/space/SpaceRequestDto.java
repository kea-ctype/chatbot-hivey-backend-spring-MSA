package com.hivey.sformservice.dto.space;

import com.hivey.sformservice.domain.space.Space;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class SpaceRequestDto {
    /**
     * 3.1 스페이스 생성하기
     */
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SpaceCreateRequestDto {
        @NotNull(message = "스페이스 이름을 입력해 주세요.")
        private String name;
        private String img;

        public Space toEntity(String accessCode) {
            return Space.builder()
                    .name(name)
                    .img(img)
                    .accessCode(accessCode)
                    .headcount(50)  // Default: 50
                    .status('Y')    // 새로 생성하는 것인 만큼 status의 Default 값은 'Y'이다. (삭제 X)
                    .build();
        }
    }
}
