package com.hivey.sformservice.application.space;

import com.hivey.sformservice.dao.form.FormRepository;
import com.hivey.sformservice.dao.form.FormTargetGroupRepository;
import com.hivey.sformservice.dao.form.SubmissionRepository;
import com.hivey.sformservice.dao.space.SpaceGroupRepository;
import com.hivey.sformservice.dao.space.SpaceMemberRepository;
import com.hivey.sformservice.dao.space.SpaceRepository;
import com.hivey.sformservice.domain.form.Form;
import com.hivey.sformservice.domain.form.Submission;
import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceGroup;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.dto.form.FormResponseDto.FormListResponseDto;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto.SpaceGroupListRes;
import com.hivey.sformservice.dto.space.SpaceRequestDto.SpaceCreateReq;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceCreateRes;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceInfoRes;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceListRes;
import com.hivey.sformservice.global.common.RandomString;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.hivey.sformservice.global.config.BaseResponseStatus.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class SpaceServiceImpl implements SpaceService{

    private final SpaceRepository spaceRepository;
    private final FormRepository formRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final SpaceGroupRepository spaceGroupRepository;
    private final SubmissionRepository submissionRepository;
    private final FormTargetGroupRepository formTargetGroupRepository;

    private final Environment env;

    /**
     * 3.1 스페이스 생성하기
     * 클라이언트에서 요청한 데이터에 맞게 스페이스를 생성한다.
     */
    @Transactional
    @Override
    public SpaceCreateRes createSpace(Long userId, SpaceCreateReq spaceCreateReq) {
        log.info("start");
        log.info("userId : {}", userId);

        // 각 스페이스마다 랜덤 문자열을 생성함으로써 고유 링크를 생성한다.
        // 여기서는 테스트용으로 스페이스를 만들 것이기 때문에 작은 범위를 선언했다. 이후 규모가 커지면, 이 부분을 수정해야 한다.

        RandomString randomString = new RandomString(10);
        String accessCode = randomString.nextString();
        log.info("accesscode : {}", accessCode);

        log.debug("isPresent(): {}", spaceRepository.findOneByAccessCode(accessCode).isPresent());

        while (spaceRepository.findOneByAccessCode(accessCode).isPresent()) {
            // 만약 이미 링크가 존재한다면 다시 생성한다.
            log.debug("input");
            accessCode = randomString.nextString();
            log.debug("failed");

        }

        // 생성한 링크와 함께 새로운 스페이스를 만든다.
        Space newSpace = spaceRepository.save(spaceCreateReq.toEntity(accessCode));
        log.debug("newSpace : {}", newSpace);
        log.debug("spaceId : {}", newSpace.getSpaceId());
        log.debug("accessCode : {}", newSpace.getAccessCode());

        // 생성한 새로운 스페이스에 Default 그룹을 생성한다.
        SpaceGroup spaceGroup = spaceGroupRepository.save(SpaceGroup.builder()
                .space(newSpace)
                .name("Default")
                .build());

        // 요청한 스페이스의 관리자에 생성한 사용자를 추가한다.
        SpaceMember spaceMember = spaceMemberRepository.save(SpaceMember.builder()
                .space(newSpace)
                .userId(userId)
                .group(spaceGroup)
                .position('M')
                .status('Y')
                .build());

//        SpaceCreateRes spaceCreateResponseDto = new ModelMapper().map(newSpace, SpaceCreateRes.class);
//        log.debug("spaceId2 : {}", spaceCreateResponseDto.getSpaceId());
        return SpaceCreateRes.builder()
                .spaceId(newSpace.getSpaceId())
                .accessCode(newSpace.getAccessCode())
                .memberId(spaceMember.getMemberId())
                .build();
    }

    /**
     * 3.6 스페이스 조회하기
     * 클라이언트에서 요청한 스페이스의 정보를 반환하기 이전에 해당 사용자가 관리자인지 참여자인지를 알려준다.
     */
    @Override
    @Transactional
    public SpaceInfoRes getSpace(Long spaceId, Long userId) {

        log.debug("start");
        // 스페이스 식별 번호에 해당하는 스페이스 엔티티를 가져온다.
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_SPACE));
        log.debug("spaceId : {}", space.getSpaceId());

        // 스페이스의 설문 목록을 조회한다.
        List<Form> forms = formRepository.findAllBySpace(space);

//        List<FormListResponseDto> formListResponseDtos = forms.stream()
//                .map(form -> new ModelMapper().map(form, FormListResponseDto.class))
//                .collect(Collectors.toList());

        // 스페이스 멤버 정보를 조회한다.
        SpaceMember spaceMember = spaceMemberRepository.findOneByUserIdAndSpace(userId, space)
                .orElseThrow(() -> new CustomException(NOT_EXISTS_SPACE_MEMBER));

        List<FormListResponseDto> formListResponseDtos = forms.stream()
                .map(form -> FormListResponseDto.builder()
                        .formId(form.getFormId())
                        .title(form.getTitle())
                        .startDate(form.getStartDate())
                        .endDate(form.getEndDate())
                        .isTarget(form.getIsMandatory() == 'Y' || formTargetGroupRepository.findAllByGroupAndForm(spaceMember.getGroup(), form).size() > 0)
                        .isSubmit(submissionRepository.findOneByFormAndMember(form, spaceMember).isPresent() && submissionRepository.findOneByFormAndMember(form, spaceMember).get().getIsSubmit() == 'Y')
                        .build())
                .collect(Collectors.toList());

        if (spaceMember.getPosition() == 'M') {
            // 해당 사용자가 스페이스의 관리자인 경우
            // 스페이스 참여 인원수, 스페이스 그룹 정보, 스페이스 그룹당 참여자 정보를 넣어준다.

            int memberCount = spaceMemberRepository.findAllBySpace(space).size();

            List<SpaceGroup> spaceGroups = spaceGroupRepository.findAllBySpace(space);

            List<SpaceGroupListRes> spaceGroupListResponseDtos = spaceGroups.stream()
                    .map(spaceGroup -> new ModelMapper().map(spaceGroup, SpaceGroupListRes.class))
                    .collect(Collectors.toList());

            return SpaceInfoRes.builder()
                    .name(space.getName())
                    .img(space.getImg())
                    .forms(formListResponseDtos)
                    .memberCount(memberCount)
                    .groups(spaceGroupListResponseDtos)
                    .build();

        } else if (spaceMember.getPosition() == 'P') {
            // 해당 사용자가 스페이스의 참여자인 경우
            return SpaceInfoRes.builder()
                    .name(space.getName())
                    .img(space.getImg())
                    .forms(formListResponseDtos)
                    .build();
        }

        throw new CustomException(FAILED_TO_GET_SPACE_INFO);
    }

    /**
     * 3.4 참여한 스페이스 목록 불러오기
     */
    public List<SpaceListRes> findAllByUserId(Long userId) {
        List<SpaceListRes> spaceListDtosAsManager = new ArrayList<>();
        List<SpaceListRes> spaceListDtosAsParticipant = new ArrayList<>();
        List<SpaceListRes> spaceListDtos;


        // SpaceMember 테이블로부터 사용자가 가입한 스페이스 정보를 가져온다.
        List<SpaceMember> spaceMembers = spaceMemberRepository.findAllByUserId(userId);

        for (SpaceMember spaceMember : spaceMembers) {

            if (spaceMember.getPosition() == 'M') {
                // 관리자인 경우
                spaceListDtosAsManager.add(SpaceListRes.builder().spaceId(spaceMember.getSpace().getSpaceId())
                        .name(spaceMember.getSpace().getName())
                        .img(spaceMember.getSpace().getImg())
                        .isManager(1).build());

            } else {
                // 회원인 경우
                spaceListDtosAsParticipant.add(SpaceListRes.builder()
                        .spaceId(spaceMember.getSpace().getSpaceId())
                        .name(spaceMember.getSpace().getName())
                        .img(spaceMember.getSpace().getImg())
                        .isManager(0)
                        .build());
            }
        }

        spaceListDtos = Stream.of(spaceListDtosAsManager, spaceListDtosAsParticipant)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return spaceListDtos;
    }
}
