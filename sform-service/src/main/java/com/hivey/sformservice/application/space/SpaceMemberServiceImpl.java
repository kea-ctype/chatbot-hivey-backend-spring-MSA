package com.hivey.sformservice.application.space;

import com.hivey.sformservice.dao.space.SpaceGroupRepository;
import com.hivey.sformservice.dao.space.SpaceMemberRepository;
import com.hivey.sformservice.dao.space.SpaceRepository;
import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceGroup;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.dto.member.SpaceMemberResponseDto;
import com.hivey.sformservice.dto.member.SpaceMemberResponseDto.SpaceMemberJoinRes;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.hivey.sformservice.global.config.BaseResponseStatus.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class SpaceMemberServiceImpl implements SpaceMemberService {

    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final SpaceGroupRepository spaceGroupRepository;

    /**
     * 스페이스 참여하기
     */
    @Transactional
    @Override
    public SpaceMemberJoinRes saveSpaceMember(Long userId, String link) {
        // 클라이언트가 참여한 링크에 맞는 스페이스 객체를 가져온다.
        Space space = spaceRepository.findOneByAccessCode(link).orElseThrow(() -> new CustomException(NOT_EXIST_SPACE));


        // 이미 가입한 사람인지 확인한다.
        Optional<SpaceMember> spaceMemberOptional = spaceMemberRepository.findOneByUserIdAndSpace(userId, space);

        if (spaceMemberOptional.isPresent()) {
            // 만약 이미 스페이스에 가입한 상태인 경우
            throw new CustomException(EXISTS_SPACE_MEMBER);
        }

        // 스페이스에 참여할 때 기본 그룹인 Default 그룹으로 지정해준다.
        SpaceGroup defaultSpaceGroup = spaceGroupRepository.findOneBySpaceAndName(space, "Default").orElseThrow(() -> new CustomException(FAILED_TO_JOIN_SPACE));

        SpaceMember spaceMember = spaceMemberRepository.save(SpaceMember.builder()
                .space(space)
                .userId(userId)
                .group(defaultSpaceGroup)
                .position('P')
                .status('Y')
                .build());

        return SpaceMemberJoinRes.builder()
                .spaceId(space.getSpaceId())
                .memberId(spaceMember.getMemberId())
                .build();
    }
}
