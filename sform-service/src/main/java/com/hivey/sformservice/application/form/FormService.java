package com.hivey.sformservice.application.form;


import com.hivey.sformservice.dto.form.FormResponseDto.RegisterRes;

public interface FormService {

    RegisterRes createForm(Long spaceId, Long userId);
}
