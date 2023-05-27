package com.hivey.userservice.global.error;

import com.hivey.userservice.global.config.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private final BaseResponseStatus exceptionStatus;
}
