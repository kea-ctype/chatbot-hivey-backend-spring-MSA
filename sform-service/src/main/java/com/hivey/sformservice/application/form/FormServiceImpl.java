package com.hivey.sformservice.application.form;

import com.hivey.sformservice.dao.form.*;
import com.hivey.sformservice.dao.space.SpaceGroupRepository;
import com.hivey.sformservice.dao.space.SpaceMemberRepository;
import com.hivey.sformservice.dao.space.SpaceRepository;
import com.hivey.sformservice.domain.answer.Answer;
import com.hivey.sformservice.domain.answer.MultipleChoiceAnswer;
import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.domain.form.FormTargetGroup;
import com.hivey.sformservice.domain.form.Submission;
import com.hivey.sformservice.domain.question.MultipleChoiceOption;
import com.hivey.sformservice.domain.question.Question;
import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceGroup;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.dto.form.FormRequestDto;
import com.hivey.sformservice.dto.form.FormRequestDto.*;
import com.hivey.sformservice.dto.form.FormRequestDto.ShortAnswerReq;
import com.hivey.sformservice.dto.form.FormResponseDto.*;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import static com.hivey.sformservice.global.config.BaseResponseStatus.*;

@Slf4j
@Service
// final 있는 필드만 생성자 만들어줌 -> 이렇게 하면 @Autowired 역할도 같이 해주는 건가? 네
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
    private final MultipleChoiceAnswerRepository multipleChoiceAnswerRepository;
    private final AnswerRepository answerRepository;




    /**
     * 4.1 설문지 생성하기 (+ 버튼) !!
     */
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

    /**
     * 4.2 설문지 생성하기 : All
     */
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

    /**
     * 4.3 스페이스의 모든 설문 불러오기
     */
    @Override
    @Transactional
    public List<FormListBySpaceRes> getSpaceForms(Long spaceId, Long userId) {

        List<FormListBySpaceRes> getSpaceForms = new ArrayList<>();

        //spaceId로 space 정보 가져오기
        Space findSpace = spaceRepository.findById(spaceId).orElseThrow(() -> new CustomException(NOT_EXIST_SPACE));

        //userId가 해당 space member인지 확인
        SpaceMember spaceMember = spaceMemberRepository.findOneByUserIdAndSpace(userId, findSpace).orElseThrow(() -> new CustomException(NOT_EXISTS_SPACE_MEMBER));

        List<Form> forms = formRepository.findAllBySpace(findSpace);

        for (Form f : forms) {
            // 각 설문에 대한 사용자의 참여 현황을 가져온다.
            boolean checkJoin = false;

            checkJoin = submissionRepository
                    .findOneByFormAndMember(f, spaceMember).isPresent();

            if (f.getStartDate() != null && f.getEndDate() != null) {
                getSpaceForms.add(FormListBySpaceRes.builder()
                        .formId(f.getFormId())
                        .title(f.getTitle())
                        .startDate(f.getStartDate())
                        .endDate(f.getEndDate())
                        .checkJoin(checkJoin)
                        .build());
            }

        }

        return getSpaceForms;
    }

    /**
     * 4.4 특정 설문지 불러오기(질문 조회하기)
     */
    @Override
    @Transactional
    public RegisterFormRes getForm(Long formId) {
        //form 정보 가져오기
        Form form = formRepository.findById(formId).orElseThrow(() -> new CustomException(NOT_EXISTS_FORM));
        //form status = 'Y'
        if (form.getStatus() == 'Y') {
            char isMandatory = form.getIsMandatory();
            //선택 참여 정보 가져오기
            List<FormTargetGroup> groups = formTargetGroupRepository.findAllByForm(form);

            // Question 정보 가져오기
            List<Question> questions = questionRepository.findAllByForm(form);
            List<RegisterQuestionRes> getQuestions = new ArrayList<>();
            List<RegisterGroupRes> getGroups = new ArrayList<>();

            //선택 참여일 경우
            if (isMandatory == 'N') {
                //그룹 정보 가져오기
                for (int i = 0; i < groups.size(); i++) {
                    FormTargetGroup formTargetGroup = formTargetGroupRepository.findById(groups.get(i).getTargetId()).orElseThrow(() -> new CustomException(NOT_EXISTS_FORM_TARGET_GROUP));
                    getGroups.add(RegisterGroupRes.builder()
                            .groupId(formTargetGroup.getGroup().getGroupId())
                            .groupName(formTargetGroup.getGroup().getName())
                            .build());
                }
            }
            for (Question q : questions) {
                //question status = 'Y'
                if (q.getStatus() == 'Y') {
                    //객관식 질문일 경우
                    if (q.getType() == 'M') {
                        // Question Option 정보 가져오기
                        List<MultipleChoiceOption> options = multipleChoiceOptionRepository.findAllByQuestion(q);
                        List<RegisterOptionRes> getOptions = new ArrayList<>();
                        for (MultipleChoiceOption option : options) {
                            //Option status = 'Y'
                            if (option.getStatus() == 'Y') {
                                getOptions.add(RegisterOptionRes.builder()
                                        .optionId(option.getOptionId())
                                        .option(option.getOptionContent())
                                        .build());
                            }
                        }
                        getQuestions.add(RegisterQuestionRes.builder()
                                .questionId(q.getQuestionId())
                                .type(q.getType())
                                .title(q.getTitle())
                                .content(q.getContent())
                                .options(getOptions)
                                .build());

                    } else {
                        getQuestions.add(RegisterQuestionRes.builder()
                                .questionId(q.getQuestionId())
                                .type(q.getType())
                                .title(q.getTitle())
                                .content(q.getContent())
                                .build());

                        log.debug(getQuestions.get(getQuestions.size() - 1).getQuestionId().toString());

                    }
                }
            }
            RegisterFormRes getForm = RegisterFormRes.builder()
                    .formId(formId)
                    .title(form.getTitle())
                    .content(form.getContent())
                    .startDate(form.getStartDate())
                    .endDate(form.getEndDate())
                    .isAnonymous(form.getIsAnonymous())
                    .isMandatory(form.getIsMandatory())
                    .groups(getGroups)
                    .questions(getQuestions)
                    .build();

            return getForm;
        } else {
            //form status='N'이면 존재하지 않은 form validation
            throw new CustomException(NOT_EXISTS_FORM);
        }
    }

    /**
     * 5.1 설문 참여하기(=설문 응답하기)
     */
    @Transactional
    @Override
    public char saveFormAnswer(Long formId, Long userId, FormAnswerReq formAnswerReq) {
        // 설문 식별 번호로부터 설문 객체와 스페이스 객체를 가져온다.
        Form form = formRepository.findById(formId).orElseThrow(() -> new CustomException(NOT_EXISTS_FORM));
        Space space = form.getSpace();

        log.debug("Form title: {}", form.getTitle());
        log.debug("Space name: {}", space.getName());

        SpaceMember spaceMember = spaceMemberRepository.findOneByUserIdAndSpace(userId, space).orElseThrow(() -> new CustomException(NOT_EXISTS_SPACE_MEMBER));


        // formAnswerRequestDto로부터 받은 값을 데이터베이스에 저장한다.

        // 객관식 질문에 대한 응답을 저장한다.
        for (MultipleChoiceAnswerReq multipleChoiceAnswerRequestDto : formAnswerReq.getMultipleChoiceAnswers()) {
            Question question = questionRepository.findById(multipleChoiceAnswerRequestDto.getQuestionId()).orElseThrow(() -> new CustomException(NOT_EXISTS_QUESTION));

            List<MultipleChoiceOption> options = new ArrayList<>();

            for (Long optionId : multipleChoiceAnswerRequestDto.getOptionIds()) {
                options.add(multipleChoiceOptionRepository.findById(optionId).orElseThrow(() -> new CustomException(NOT_EXISTS_OPTION)));
            }

            for (MultipleChoiceOption option : options) {

                multipleChoiceAnswerRepository.save(MultipleChoiceAnswer.builder()
                        .member(spaceMember)
                        .option(option)
                        .status('Y')
                        .build());

                log.debug("Option content: {}", option.getOptionContent());
            }
        }

        // 주관식 질문에 대한 응답을 저장한다.
        for (ShortAnswerReq shortAnswerRequestDto : formAnswerReq.getShortAnswerResponses()) {
            Question question = questionRepository.findById(shortAnswerRequestDto.getQuestionId()).orElseThrow(() -> new CustomException(NOT_EXISTS_QUESTION));
            String shortAnswer = shortAnswerRequestDto.getAnswer();

            log.debug("Question id: {}", question.getQuestionId());
            log.debug("Short answer: {}", shortAnswer);

            Answer answerRes = answerRepository.save(Answer.builder()
                    .member(spaceMember)
                    .question(question)
                    .answer(shortAnswer)
                    .status('Y')
                    .build());
        }

        // 해당 멤버의 설문 참여 현황을 업데이트한다.
        Submission submission = submissionRepository.findOneByFormAndMember(form, spaceMember).orElseThrow(() -> new CustomException(NOT_EXISTS_SUBMISSION));
        submission.updateIsSubmit('Y');

        Submission updatedSubmission = submissionRepository.save(submission);
        return updatedSubmission.getIsSubmit();
    }

    /**
     * 특정 설문지 조회 - 질문 & 답변
     */
    @Override
    @Transactional
    public GetFormRes getFormAndAnswer(Long formId, Long userId) {

        ModelMapper modelMapper = new ModelMapper();
        //form 존재 여부
        Form form = formRepository.findById(formId).orElseThrow(() -> new CustomException(NOT_EXISTS_FORM));

        //space 존재 여부
        Space space = spaceRepository.findById(form.getSpace().getSpaceId()).orElseThrow(() -> new CustomException(NOT_EXIST_SPACE));

        //user가 해당 스페이스 멤버인지 확인
        SpaceMember spaceMember = spaceMemberRepository.findOneByUserIdAndSpace(userId, space).orElseThrow(() -> new CustomException(NOT_EXISTS_SPACE_MEMBER));

        //응답 여부
        Submission submission = submissionRepository.findOneByFormAndMember(form, spaceMember).orElseThrow(() -> new CustomException(NOT_EXISTS_SUBMISSION));
        if (submission.getIsSubmit() == 'Y') {
            //question Response 생성
            List<GetQuestionRes> questionRes = new ArrayList<>();

            //질문 리스트
            List<Question> questions = questionRepository.findAllByForm(form);
            for (Question question : questions) {
                //객관식 질문일 경우
                if (question.getType() == 'M') {
                    // Question Option 정보 가져오기
                    List<MultipleChoiceOption> options = multipleChoiceOptionRepository.findAllByQuestion(question);
                    //option Response 생성
                    List<GetOptionRes> optionRes = new ArrayList<>();
                    //객관식 옵션 응답 Response 생성
                    List<GetAnswerOptionRes> answerOptionRes = new ArrayList<>();
                    for (MultipleChoiceOption option : options) {
                        //option Response 추가
                        optionRes.add(modelMapper.map(option, GetOptionRes.class));


                        //optionAnswer Response 추가
                        List<MultipleChoiceAnswer> multiAnswer = multipleChoiceAnswerRepository.findAllByOptionAndMember(option, spaceMember);
                        for (MultipleChoiceAnswer optionAnswer : multiAnswer) {
                            answerOptionRes.add(modelMapper.map(optionAnswer, GetAnswerOptionRes.class));


                        }
                    }
                    //질문 Response 추가
                    questionRes.add(GetQuestionRes.builder()
                            .questionId(question.getQuestionId())
                            .type(question.getType())
                            .title(question.getTitle())
                            .content(question.getContent())
                            .options(optionRes)
                            .answerOptions(answerOptionRes)
                            .build());

                } else {
                    //서술형 응답 Response 추가
                    Answer shortAnswer = answerRepository.findOneByQuestionAndMember(question, spaceMember);
                    GetAnswerRes getAnswerRes = modelMapper.map(shortAnswer, GetAnswerRes.class);
                    questionRes.add(GetQuestionRes.builder()
                            .questionId(question.getQuestionId())
                            .type(question.getType())
                            .title(question.getTitle())
                            .content(question.getContent())
                            .answer(getAnswerRes)
                            .build());

                }
            }
            //form Response 추가
            GetFormRes getFormRes = GetFormRes.builder()
                    .formId(form.getFormId())
                    .title(form.getTitle())
                    .content(form.getContent())
                    .creator(userId)
                    .startDate(form.getStartDate())
                    .endDate(form.getEndDate())
                    .questions(questionRes)
                    .build();
            return getFormRes;

        }

        throw new CustomException(FAILED_TO_GET_FORM);
    }
}

