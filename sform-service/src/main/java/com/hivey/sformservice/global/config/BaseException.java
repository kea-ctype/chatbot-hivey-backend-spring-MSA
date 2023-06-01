package com.hivey.sformservice.global.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
    private final BaseResponseStatus status;  //BaseResoinseStatus 객체에 매핑
}