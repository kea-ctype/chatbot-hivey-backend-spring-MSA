package com.hivey.sformservice.api.form;

import com.hivey.sformservice.application.form.FormService;
import com.hivey.sformservice.dto.form.FormRequestDto;
import com.hivey.sformservice.dto.form.FormResponseDto;
import com.hivey.sformservice.dto.form.FormResponseDto.RegisterRes;
import com.hivey.sformservice.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.hivey.sformservice.dto.form.FormRequestDto.*;
import static com.hivey.sformservice.dto.form.FormResponseDto.*;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FormController {

    private final FormService formService;

    /**
     * 4.1 설문지 생성하기 (+ 버튼) !!
     */
    @PostMapping("/{spaceId}/{userId}")
    public BaseResponse<RegisterRes> createForm(@PathVariable Long spaceId, @PathVariable Long userId){
        return new BaseResponse<>(formService.createForm(spaceId, userId));
    }

    /**
     * 4.2 설문지 생성하기 : All
     */
    @PatchMapping("/{formId}")
    public BaseResponse<RegisterFormRes> registerFormAll(@PathVariable Long formId, @RequestBody RegisterFormReq registerFormReq) {
        return new BaseResponse<>(formService.registerForm(formId, registerFormReq));
    }


}
