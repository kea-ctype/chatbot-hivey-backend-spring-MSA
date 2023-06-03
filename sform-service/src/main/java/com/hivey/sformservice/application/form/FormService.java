package com.hivey.sformservice.application.form;


import com.hivey.sformservice.dto.form.FormRequestDto;
import com.hivey.sformservice.dto.form.FormRequestDto.FormAnswerReq;
import com.hivey.sformservice.dto.form.FormRequestDto.RegisterFormReq;
import com.hivey.sformservice.dto.form.FormResponseDto;
import com.hivey.sformservice.dto.form.FormResponseDto.FormListBySpaceRes;
import com.hivey.sformservice.dto.form.FormResponseDto.GetFormRes;
import com.hivey.sformservice.dto.form.FormResponseDto.RegisterFormRes;
import com.hivey.sformservice.dto.form.FormResponseDto.RegisterRes;

import java.util.List;

public interface FormService {

    RegisterRes createForm(Long spaceId, Long userId);

    RegisterFormRes registerForm(Long formId, RegisterFormReq registerFormReq);

    List<FormListBySpaceRes> getSpaceForms(Long spaceId, Long userId);

    RegisterFormRes getForm(Long formId);

    char saveFormAnswer(Long formId, Long userId, FormAnswerReq formAnswerReq);

    GetFormRes getFormAndAnswer(Long formId, Long userId);


}
