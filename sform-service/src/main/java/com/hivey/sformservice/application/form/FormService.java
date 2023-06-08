package com.hivey.sformservice.application.form;


import com.hivey.sformservice.dto.form.FormRequestDto;
import com.hivey.sformservice.dto.form.FormRequestDto.FormAnswerReq;
import com.hivey.sformservice.dto.form.FormRequestDto.RegisterFormReq;
import com.hivey.sformservice.dto.form.FormResponseDto;
import com.hivey.sformservice.dto.form.FormResponseDto.*;

import java.util.List;

public interface FormService {

    RegisterRes createForm(Long spaceId, Long userId);

    RegisterFormRes registerForm(Long formId, RegisterFormReq registerFormReq);

    List<FormListBySpaceRes> getSpaceForms(Long spaceId, Long userId);

    RegisterFormRes getForm(Long formId);

    char saveFormAnswer(Long formId, Long userId, FormAnswerReq formAnswerReq);

    GetFormRes getFormAndAnswer(Long formId, Long userId);

    List<FormResponseDto.FormGetSubmissionList> findSubmissionListByFormId(Long formId);

    List<FormResponseDto.TargetGroupsByFormRes> findAllTargetGroupsByFormId(Long formId);

    FormMandatoryOrNotRes findIsMandatoryByForm(Long formId);

    GetFormResultRes getFormResult(Long formId);

    SubmissionByUserResponseDto getSubmissionByUser(Long formId, Long memberId);


}
