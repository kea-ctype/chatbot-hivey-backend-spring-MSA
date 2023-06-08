package com.hivey.sformservice.application.space;

import com.hivey.sformservice.client.UserServiceClient;
import com.hivey.sformservice.dao.space.SpaceGroupRepository;
import com.hivey.sformservice.dao.space.SpaceMemberRepository;
import com.hivey.sformservice.dao.space.SpaceRepository;
import com.hivey.sformservice.domain.space.Space;
import com.hivey.sformservice.domain.space.SpaceGroup;
import com.hivey.sformservice.domain.space.SpaceMember;
import com.hivey.sformservice.dto.form.FormDataDto.GetUserRes;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto.SpaceGroupGetListRes;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto.SpaceGroupListRes;
import com.hivey.sformservice.dto.member.SpaceMemberResponseDto.SpaceMemberByGroupRes;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hivey.sformservice.global.config.BaseResponseStatus.NOT_EXISTS_SPACE_GROUP;
import static com.hivey.sformservice.global.config.BaseResponseStatus.NOT_EXIST_SPACE;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpaceGroupServiceImpl implements SpaceGroupService {

    private final SpaceGroupRepository spaceGroupRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final UserServiceClient userServiceClient;


    /**
     * 3.1 모든 스페이스 그룹 목록 불러오기
     */
    public List<SpaceGroupGetListRes> findAllBySpaceId(Long spaceId) {
        // 스페이스 식별 번호를 통해 스페이스 객체를 가져온다.
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new CustomException(NOT_EXIST_SPACE));

        List<SpaceGroup> spaceGroups = spaceGroupRepository.findAllBySpace(space);

        return spaceGroups.stream()
                .map(spaceGroup -> new ModelMapper().map(spaceGroup, SpaceGroupGetListRes.class))
                .collect(Collectors.toList());

    }

    /**
     * 3.2 모든 스페이스 그룹과 그 멤버 목록 불러오기
     */
    public List<SpaceGroupListRes> findGroupsAndMembersBySpace(Long spaceId) {
        // 스페이스 식별 번호를 통해 스페이스 객체를 가져온다.
        Space space = spaceRepository.findById(spaceId).orElseThrow(() -> new CustomException(NOT_EXIST_SPACE));

        // 스페이스에 해당하는 그룹 목록을 가져온다.
        List<SpaceGroup> spaceGroups = spaceGroupRepository.findAllBySpace(space);


        List<SpaceMemberByGroupRes> spaceMemberByGroupRes = new ArrayList<>();
        List<SpaceGroupListRes> spaceGroupListRes = new ArrayList<>();

        for (SpaceGroup spaceGroup : spaceGroups) {

            //spacegroup으로 spacemember 정보 가져오기
            List<SpaceMember> spaceMembers = spaceMemberRepository.findAllByGroup(spaceGroup);
            for (SpaceMember spaceMember : spaceMembers) {
                GetUserRes getUserRes = userServiceClient.getUsers(spaceMember.getUserId());
                //spacemember res 추가
                spaceMemberByGroupRes.add(SpaceMemberByGroupRes.builder()
                        .memberId(spaceMember.getMemberId())
                        .name(getUserRes.getName())
                        .email(getUserRes.getEmail())
                        .position(spaceMember.getPosition())
                        .build());
            }

            spaceGroupListRes.add(SpaceGroupListRes.builder()
                            .members(spaceMemberByGroupRes)
                            .name(spaceGroup.getName())
                    .build());

        }



        return spaceGroupListRes;
    }

    /**
     * 3.3 특정 스페이스 그룹 멤버 목록만 불러오기
     */
    public List<SpaceMemberByGroupRes> findSpaceMemebersByGroups(Long groupId) {
        // 스페이스 그룹 식별 번호를 통해 스페이스 그룹 객체를 가져온다.
        SpaceGroup spaceGroup = spaceGroupRepository.findById(groupId).orElseThrow(() -> new CustomException(NOT_EXISTS_SPACE_GROUP));

        // 스페이스 그룹 객체를 통해 스페이스 멤버 목록을 가져온다.
        List<SpaceMember> spaceMembers = spaceMemberRepository.findAllByGroup(spaceGroup);
        List<SpaceMemberByGroupRes> spaceMemberByGroupRes = new ArrayList<>();
        for (SpaceMember spaceMember : spaceMembers) {
            //spacemember res 추가
            spaceMemberByGroupRes.add(SpaceMemberByGroupRes.builder()
                    .memberId(spaceMember.getMemberId())
                    .name(userServiceClient.getUsers(spaceMember.getUserId()).getName())
                    .position(spaceMember.getPosition())
                    .build());
        }



        return spaceMemberByGroupRes;
    }
}
