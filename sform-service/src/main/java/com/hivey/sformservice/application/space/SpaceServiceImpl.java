package com.hivey.sformservice.application.space;

import com.hivey.sformservice.dao.form.FormRepository;
import com.hivey.sformservice.dao.space.SpaceGroupRepository;
import com.hivey.sformservice.dao.space.SpaceMemberRepository;
import com.hivey.sformservice.dao.space.SpaceRepository;
import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceGroup;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.dto.space.SpaceRequestDto;
import com.hivey.sformservice.dto.space.SpaceRequestDto.SpaceCreateRequestDto;
import com.hivey.sformservice.dto.space.SpaceResponseDto;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceCreateResponseDto;
import com.hivey.sformservice.global.common.RandomString;
import com.hivey.sformservice.global.config.BaseResponseStatus;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class SpaceServiceImpl implements SpaceService{

    private final SpaceRepository spaceRepository;
//    private final FormRepository formRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final SpaceGroupRepository spaceGroupRepository;

    /**
     * 3.1 스페이스 생성하기
     * 클라이언트에서 요청한 데이터에 맞게 스페이스를 생성한다.
     */
    @Transactional
    @Override
    public SpaceCreateResponseDto createSpace(Long userId, SpaceCreateRequestDto spaceCreateRequestDto) {
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

        Space originalSpace = spaceRepository.findById(1L).orElseThrow(() -> new CustomException(BaseResponseStatus.NOT_EXIST_SPACE));
        log.debug("spaceId = 1 : {}", originalSpace.getSpaceId());


        // 생성한 링크와 함께 새로운 스페이스를 만든다.
        Space newSpace = spaceRepository.save(spaceCreateRequestDto.toEntity(accessCode));
        log.debug("newSpace : {}", newSpace);
        log.debug("spaceId : {}", newSpace.getSpaceId());
        log.debug("accessCode : {}", newSpace.getAccessCode());

        // 생성한 새로운 스페이스에 Default 그룹을 생성한다.
        SpaceGroup spaceGroup = spaceGroupRepository.save(SpaceGroup.builder()
                .space(newSpace)
                .name("Default")
                .build());


        // 요청한 스페이스의 관리자에 생성한 사용자를 추가한다.
        spaceMemberRepository.save(SpaceMember.builder()
                .space(newSpace)
                .userId(userId)
                .group(spaceGroup)
                .position('M')
                .status('Y')
                .build());

        SpaceCreateResponseDto spaceCreateResponseDto = new ModelMapper().map(newSpace, SpaceCreateResponseDto.class);

        log.debug("spaceId2 : {}", spaceCreateResponseDto.getSpaceId());
        return spaceCreateResponseDto;
    }
}
