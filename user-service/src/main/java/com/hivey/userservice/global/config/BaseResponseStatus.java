package com.hivey.userservice.global.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */

    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해 주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_REQUEST_BODY(false, 2005, "요청 데이터가 잘못되었습니다."),


    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해 주세요."),

    // [POST] /users
    EMPTY_EMAIL(false, 2015, "이메일을 입력해 주세요."),
    EMPTY_PASSWORD(false, 2016, "비밀번호를 입력해 주세요."),
    INVALID_EMAIL(false, 2017, "이메일 형식을 확인해 주세요."),
    INVALID_PASSWORD(false, 2018, "비밀번호는 영어와 숫자만 사용하세요."),
    EXISTS_EMAIL(false,2019,"중복된 이메일입니다."),
    NOT_EXISTS_USER(false, 2020, "존재하지 않는 회원입니다."),
    EXISTS_USER(false, 2021, "이미 존재하는 회원입니다."),
    FAILED_TO_UPDATE_USER(false,2022,"사용자 정보 수정에 실패하였습니다"),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"비밀번호가 틀렸습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),


    /**
     * 5000 : Space
     */
    INVALID_SPACE_ID(false, 5000, "스페이스 아이디 값을 확인해 주세요."),
    NOT_EXIST_SPACE(false, 5001, "존재하지 않는 스페이스입니다."),
    FAILED_TO_CREATE_SPACE(false, 5002, "스페이스 생성에 실패하였습니다"),

    FAILED_TO_GET_SPACE_INFO(false, 5005, "스페이스 조회에 실패하였습니다."),
    FAILED_TO_JOIN_SPACE(false, 5006, "스페이스 가입이 실패하였습니다."),
    FAILED_TO_UPDATE_SPACE(false, 5007, "스페이스 수정에 실패하였습니다."),
    FAILED_TO_DELETE_SPACE(false, 5008, "스페이스 삭제가 실패하였습니다."),
    ALREADY_DELETED_SPACE(false, 5009, "이미 삭제된 스페이스입니다."),

    /**
     * 6000 : Space Member
     */
    EMPTY_SPACE_MEMBER_ID(false, 6000, "스페이스 멤버 아이디 값을 확인해 주세요."),
    NOT_EXISTS_SPACE_MEMBER(false, 6001, "존재하지 않는 스페이스 멤버입니다."),
    NOT_SPACE_MANAGER(false, 6002, "스페이스의 관리자가 아니므로 권한이 존재하지 없습니다."),
    FAILED_TO_SET_GROUP(false, 6005, "그룹 설정이 실패하였습니다."),
    FAILED_TO_CHANGE_GROUP(false, 6006, "그룹 변경이 실패하였습니다."),
    FAILED_TO_INIT_GROUP(false, 6007, "그룹 초기화가 실패하였습니다."),
    ALREADY_JOINED_GROUP(false, 6008, "이미 변경하려는 그룹에 소속되어 있습니다."),
    EXISTS_SPACE_MEMBER(false, 6010, "이미 스페이스의 멤버입니다."),
    INVALID_SPACE_AND_MEMBER(false, 6011, "스페이스와 멤버의 값이 올바르지 않습니다."),
    FAILED_TO_CHANGE_GROUP_AFTER_DELETE(false, 6012, "해당 그룹에 소속된 멤버가 있습니다."),

    // SUCCESS로 대체
//    SET_SPACE_MEMBER_TO_GROUP(true, 6015, "스페이스 멤버의 그룹이 설정되었습니다."),
//    CHANGE_SPACE_MEMBER_GROUP(true, 6016, "스페이스 멤버의 그룹이 변경되었습니다."),
//    INIT_SPACE_MEMBER_GROUP(true, 6017, "스페이스 멤버의 그룹이 초기화되었습니다."),

    /**
     * 7000 : Space Group
     */
    SPACE_GROUPS_EMPTY_SPACE_GROUP_ID(false, 7000, "스페이스 그룹 아이디를 확인해 주세요."),
    NOT_EXISTS_SPACE_GROUP(false, 7001, "존재하지 않는 그룹입니다."),
    DUPLICATED_SPACE_GROUP_NAME(false, 7002, "이미 존재하는 그룹명입니다."),
    FAILED_TO_UPDATE_SPACE_GROUP(false, 7004, "스페이스 그룹 수정에 실패하였습니다."),
    CANNOT_DELETE_SPACE_GROUP(false, 7005, "해당 스페이스 그룹은 삭제할 수 없습니다."),
    FAILED_TO_DELETE_SPACE_GROUP(false, 7006, "스페이스 그룹 삭제에 실패하였습니다."),

    /**
     * 8000 : Form
     */
    EMPTY_FORM_ID(false, 8000, "설문 아이디 값을 확인해 주세요."),
    FAILED_TO_SAVE_FORM_ANSWER(false, 8001, "설문 응답 저장에 실패하였습니다."),
    NOT_EXISTS_FORM(false, 8002, "존재하지 않는 설문 폼입니다."),
    NOT_EXISTS_FORM_TARGET_GROUP(false, 8003, "존재하지 않는 설문 참여 그룹입니다."),
    NOT_FORM_CREATOR(false, 8004, "해당 설문지를 만든 사용자가 아닙니다."),
    DELETE_FORM(false, 8005, "삭제된 설문지입니다."),
    FAILED_TO_REGISTER_FORM(false, 8010, "설문지 생성이 실패하였습니다."),
    FAILED_TO_GET_FORM(true, 8011, "설문지를 불러오는 데 실패하였습니다."),
    SUCCESS_TO_REGISTER_FORM(true, 8020, "설문지 생성에 성공하였습니다."),
    ADD_TO_TARGET_GROUP(false, 8030, "그룹을 선택해주세요."),



    /**
     * 9000 : Question
     */
    EMPTY_QUESTION_ID(false, 9000, "문항 아이디 값을 확인해 주세요."),
    NOT_EXISTS_QUESTION(false, 9001, "존재하지 않는 설문 문항입니다."),
    FAILED_TO_REGISTER_QUESTION(false, 9010, "설문 문항 생성이 실패하였습니다."),

    /**
     * 10000 : Option
     */
    NOT_EXISTS_OPTION(false, 10000, "존재하지 않는 설문 문항 옵션입니다."),

    /**
     * 11000 : Submission
     */
    NOT_EXISTS_SUBMISSION(false, 11000, "존재하지 않는 설문 참여 현황입니다."),
    ALREADY_SUBMISSION(false, 11001, "이미 설문 응답한 사람이 있어 수정이 불가능합니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}