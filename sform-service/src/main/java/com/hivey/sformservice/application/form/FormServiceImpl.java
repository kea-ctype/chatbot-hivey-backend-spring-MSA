package com.hivey.sformservice.application.form;

import com.hivey.sformservice.dao.form.FormRepository;
import com.hivey.sformservice.dao.space.SpaceMemberRepository;
import com.hivey.sformservice.dao.space.SpaceRepository;
import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.dto.form.FormResponseDto.RegisterRes;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hivey.sformservice.global.config.BaseResponseStatus.*;

@Slf4j
@Service
// final 있는 필드만 생성자 만들어줌 -> 이렇게 하면 @Autowired 역할도 같이 해주는 건가?
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;


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
}
