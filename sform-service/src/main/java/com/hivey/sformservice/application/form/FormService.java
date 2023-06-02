package com.hivey.sformservice.application.form;


import com.hivey.sformservice.dto.form.FormRequestDto;
import com.hivey.sformservice.dto.form.FormRequestDto.RegisterFormReq;
import com.hivey.sformservice.dto.form.FormResponseDto;
import com.hivey.sformservice.dto.form.FormResponseDto.RegisterFormRes;
import com.hivey.sformservice.dto.form.FormResponseDto.RegisterRes;

public interface FormService {

    RegisterRes createForm(Long spaceId, Long userId);

    RegisterFormRes registerForm(Long formId, RegisterFormReq registerFormReq);
}
