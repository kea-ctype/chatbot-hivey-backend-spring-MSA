package com.hivey.userservice.global.util;

import ctype.survey.hivey.global.error.CustomException;

import static ctype.survey.hivey.global.config.BaseResponseStatus.*;

/**
 * 편의를 위해 공통적으로 사용되는 메서드들을 모아 놓은 클래스
 */
public class ValidationUtil {


    /**
     * Path variable로 전달받는 user id에 대한 검증
     */
    public static void validateUserId(Long userId) {

        // Path variable에 값이 제대로 전달되었는지 확인한다.
        if (userId <= 0) {
            throw new CustomException(USERS_EMPTY_USER_ID);
        }
    }

    /**
     * 사용자 JWT 검증
     */
    public static void validateUserJwt(JwtService jwtService, Long userId) {

        // JWT로부터 사용자 식별 번호를 가져와서 비교한다. 만약 같지 않은 경우 예외를 발생시킨다.
        int jwt = jwtService.getUserIdx();

        if (userId != jwt) {
            throw new CustomException(INVALID_USER_JWT);
        }
    }

    /**
     * Path variable로 전달받는 space id에 대한 검증
     */
    public static void validateSpaceId(Long spaceId) {

        // Path variable에 값이 제대로 전달되었는지 확인한다.
        if (spaceId <= 0) {
            throw new CustomException(INVALID_SPACE_ID);
        }
    }

    /**
     * Path variable로 전달받는 member id에 대한 검증
     */
    public static void validateMemberId(Long memberId) {

        // Path variable에 값이 제대로 전달되었는지 확인한다.
        if (memberId <= 0) {
            throw new CustomException(EMPTY_SPACE_MEMBER_ID);
        }
    }

    /**
     * Path variable로 전달받는 form id에 대한 검증
     */
    public static void validateFormId(Long formId) {
        // Path variable에 값이 제대로 전달되었는지 확인한다.
        if (formId <= 0) {
            throw new CustomException(EMPTY_FORM_ID);
        }
    }

    /**
     * Path variable로 전달받는 Group id에 대한 검증
     */
    public static void validateGroupId(Long groupId) {

        // Path variable에 값이 제대로 전달되었는지 확인한다.
        if (groupId <= 0) {
            throw new CustomException(SPACE_GROUPS_EMPTY_SPACE_GROUP_ID);
        }
    }


}
