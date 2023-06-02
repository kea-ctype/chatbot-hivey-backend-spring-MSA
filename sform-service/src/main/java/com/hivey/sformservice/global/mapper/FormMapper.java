package com.hivey.sformservice.global.mapper;

import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.dto.form.FormResponseDto.FormMandatoryOrNotRes;
import org.mapstruct.factory.Mappers;

public interface FormMapper {

    FormMapper INSTANCE = Mappers.getMapper(FormMapper.class);


    /**
     * Entity (Form) -> DTO (FormMandatoryOrNotResponseDto)
     */
    FormMandatoryOrNotRes toFormMandatoryOrNotRes(Form form);
}