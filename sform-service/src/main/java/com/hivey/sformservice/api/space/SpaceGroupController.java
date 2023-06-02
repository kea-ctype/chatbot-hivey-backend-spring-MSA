package com.hivey.sformservice.api.space;

import com.hivey.sformservice.application.space.SpaceGroupService;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto.SpaceGroupGetListRes;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto.SpaceGroupListRes;
import com.hivey.sformservice.dto.member.SpaceMemberResponseDto.SpaceMemberByGroupRes;
import com.hivey.sformservice.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/spaces")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
class SpaceGroupController {

    private final SpaceGroupService spaceGroupService;


    /**
     * 3.1 모든 스페이스 그룹 목록 불러오기
     */
    @GetMapping("/{spaceId}/groups")
    public BaseResponse<List<SpaceGroupGetListRes>> getSpaceGroups(@PathVariable Long spaceId) {

        return new BaseResponse<>(spaceGroupService.findAllBySpaceId(spaceId));
    }

    /**
     * 3.2 모든 스페이스 그룹과 그 멤버 목록 불러오기
     */
    @GetMapping("{spaceId}/groups/members")
    public BaseResponse<List<SpaceGroupListRes>> getSpaceGroupsAndMembers(@PathVariable Long spaceId) {

        return new BaseResponse<>(spaceGroupService.findGroupsAndMembersBySpace(spaceId));
    }

    /**
     * 3.3 특정 스페이스 그룹 멤버 목록만 불러오기
     */
    @GetMapping("/{spaceId}/groups/{groupId}")
    public BaseResponse<List<SpaceMemberByGroupRes>> getSpaceMembersByGroup(@PathVariable Long spaceId, @PathVariable Long groupId) {

        return new BaseResponse<>(spaceGroupService.findSpaceMemebersByGroups(groupId));
    }


}
