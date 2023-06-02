package com.hivey.sformservice.api.form;

import com.hivey.sformservice.application.form.FormService;
import com.hivey.sformservice.dto.form.FormResponseDto.RegisterRes;
import com.hivey.sformservice.global.config.BaseResponse;
import com.hivey.sformservice.global.config.BaseResponseStatus;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hivey.sformservice.dto.form.FormRequestDto.*;
import static com.hivey.sformservice.dto.form.FormResponseDto.*;
import static com.hivey.sformservice.global.config.BaseResponseStatus.FAILED_TO_SAVE_FORM_ANSWER;
import static com.hivey.sformservice.global.config.BaseResponseStatus.SUCCESS;

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

    /**
     * 4.3 스페이스의 모든 설문 불러오기
     */
    @GetMapping("/{spaceId}/{userId}")
    public BaseResponse<List<FormListBySpaceRes>> getSpaceForms(@PathVariable Long spaceId, @PathVariable Long userId) {
        List<FormListBySpaceRes> getSpaceForms = formService.getSpaceForms(spaceId, userId);
        return new BaseResponse<>(getSpaceForms);
    }

    /**
     * 4.4 특정 설문지 불러오기(질문 조회하기)
     */
    @GetMapping("/{formId}")
    public BaseResponse<RegisterFormRes> getForm(@PathVariable Long formId) {

        RegisterFormRes getForm = formService.getForm(formId);
        return new BaseResponse<>(getForm);
    }

    /**
     * 5.1 설문 참여하기(응답하기)
     */
    @PostMapping("/answer/{formId}/{userId}")
    public BaseResponse<BaseResponseStatus> answerForm(@PathVariable Long formId, @PathVariable Long userId, @RequestBody FormAnswerReq formAnswerReq) {

        if (formService.saveFormAnswer(formId, userId, formAnswerReq) == 'Y') {
            return new BaseResponse<>(SUCCESS);
        } else {
            throw new CustomException(FAILED_TO_SAVE_FORM_ANSWER);
        }

    }


}
