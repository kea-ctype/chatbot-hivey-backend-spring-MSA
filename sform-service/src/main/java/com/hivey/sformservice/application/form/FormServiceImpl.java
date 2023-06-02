package com.hivey.sformservice.application.form;

import com.hivey.sformservice.dao.form.*;
import com.hivey.sformservice.dao.space.SpaceGroupRepository;
import com.hivey.sformservice.dao.space.SpaceMemberRepository;
import com.hivey.sformservice.dao.space.SpaceRepository;
import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.domain.form.Submission;
import com.hivey.sformservice.domain.question.MultipleChoiceOption;
import com.hivey.sformservice.domain.question.Question;
import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceGroup;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.dto.form.FormRequestDto;
import com.hivey.sformservice.dto.form.FormRequestDto.RegisterFormReq;
import com.hivey.sformservice.dto.form.FormRequestDto.RegisterQuestionReq;
import com.hivey.sformservice.dto.form.FormResponseDto;
import com.hivey.sformservice.dto.form.FormResponseDto.*;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.stream.Stream;

import static com.hivey.sformservice.global.config.BaseResponseStatus.*;

@Slf4j
@Service
// final 있는 필드만 생성자 만들어줌 -> 이렇게 하면 @Autowired 역할도 같이 해주는 건가?
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final SpaceGroupRepository spaceGroupRepository;
    private final SubmissionRepository submissionRepository;
    private final FormTargetGroupRepository formTargetGroupRepository;
    private final QuestionRepository questionRepository;
    private final MultipleChoiceOptionRepository multipleChoiceOptionRepository;


    @Override
    @Transactional
    public RegisterRes createForm(Long spaceId, Long userId) {
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new CustomException(NOT_EXIST_SPACE));

        SpaceMember spaceMember = spaceMemberRepository.findOneByUserIdAndSpace(userId, space).orElseThrow(() -> new CustomException(NOT_EXISTS_SPACE_MEMBER));

        Form createForm = formRepository.save(Form.builder()
                .space(space)
                .creator(spaceMember)
                .isAnonymous('N')
                .status('Y')
                .isMandatory('Y')
                .build());

        log.debug("formId: {}", createForm.getFormId());

        return new RegisterRes(createForm.getFormId());
    }

    @Override
    @Transactional
    public RegisterFormRes registerForm(Long formId, RegisterFormReq registerFormReq) {
        // Form 테이블에 식별번호 존재하는지 확인
        Form form = formRepository.findByFormId(formId).orElseThrow(() -> new CustomException(NOT_EXISTS_FORM));
        //space 존재 여부
        Space space = spaceRepository.findById(form.getSpace().getSpaceId()).orElseThrow(() -> new CustomException(NOT_EXIST_SPACE));
        List<RegisterGroupRes> groups = new ArrayList<>();
        // 해당 식별 번호가 같을 경우

        if (!Objects.equals(form.getFormId(), formId)) {
            throw new CustomException(FAILED_TO_REGISTER_FORM);
        }

        form.saveForm(registerFormReq);
        Form updatedForm = formRepository.save(form);
        char isMandatory = updatedForm.getIsMandatory();

        //선택 참여일 경우
        if (isMandatory == 'N') {
            //선택 참여일 경우 그룹 저장
            //선택 참여이지만 그룹이 없을 경우
            if (registerFormReq.getGroups() == null) {
                throw new CustomException(ADD_TO_TARGET_GROUP);
            }

            for (int i = 0; i < registerFormReq.getGroups().size(); i++) {

                int result = formTargetGroupRepository.saveGroup(formId, registerFormReq.getGroups().get(i).getGroupId());
                log.debug("form_target_group: {}", result);

                //response 그룹 정보 추가
                SpaceGroup spaceGroup = spaceGroupRepository.findById(registerFormReq.getGroups().get(i).getGroupId()).orElseThrow(() -> new CustomException(NOT_EXISTS_SPACE_GROUP));
                groups.add(RegisterGroupRes.builder()
                        .groupId(spaceGroup.getGroupId())
                        .groupName(spaceGroup.getName())
                        .build());
                //spaceMember 체크
                List<SpaceMember> spaceMembers = spaceMemberRepository.findAllBySpaceAndGroup(space, spaceGroup);
                for (SpaceMember spaceMember : spaceMembers) {
                    log.debug("space Member : {}", spaceMember.getMemberId());
                    //submission 추가
                    Submission submission = submissionRepository.save(Submission.builder()
                            .form(form)
                            .member(spaceMember)
                            .isSubmit('N')
                            .build());
                }
            }

        } else { //필수 참여일 경우
            //모든 spaceMember submission에 추가
            List<SpaceMember> spaceMembers = spaceMemberRepository.findAllBySpace(space);
            for (SpaceMember spaceMember : spaceMembers) {
                Submission submission = submissionRepository.save(Submission.builder()
                        .form(form)
                        .member(spaceMember)
                        .isSubmit('N')
                        .build());

            }
        }
        //질문 저장
        List<RegisterQuestionRes> questionList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (RegisterQuestionReq questionReq : registerFormReq.getQuestionRequests()) {
            // 객관식 질문 등록
            Question question = questionRepository.save(Question.builder()
                    .form(form)
                    .type(questionReq.getType())
                    .title(questionReq.getTitle())
                    .content(questionReq.getContent())
                    .status('Y')
                    .build());

            //질문 등록 Response 추가
            RegisterQuestionRes registerQuestionRes = modelMapper.map(question, RegisterQuestionRes.class);
            registerQuestionRes.setOptions(null);

            //질문이 객관식일 경우
            if (questionReq.getType() == 'M') {
                List<RegisterOptionRes> optionRes = new ArrayList<>();

                for (String option : questionReq.getOptions()) {
                    //질문에 대한 옵션 저장
                    MultipleChoiceOption registerOption = MultipleChoiceOption.builder()
                            .question(question)
                            .optionContent(option)
                            .status('Y')
                            .build();

                    registerOption = multipleChoiceOptionRepository.save(registerOption);

                    //옵션 Response 추가
                    RegisterOptionRes optionResItem = modelMapper.map(registerOption, RegisterOptionRes.class);
                    optionRes.add(optionResItem);
                }

                //Response : 질문에 대한 옵션 리스트 추가
                registerQuestionRes.setOptions(optionRes);
            }

            //Response : 설문지에 대한 질문 리스트 추가
            questionList.add(registerQuestionRes);
        }

        //
        RegisterFormRes registerFormRes = modelMapper.map(updatedForm, RegisterFormRes.class);

        registerFormRes.setFormId(formId);
        registerFormRes.setGroups(groups);
        registerFormRes.setQuestions(questionList);

        return registerFormRes;
    }

}

