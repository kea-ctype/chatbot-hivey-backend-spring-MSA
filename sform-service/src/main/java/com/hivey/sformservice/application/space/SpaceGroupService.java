package com.hivey.sformservice.application.space;

import com.hivey.sformservice.dto.group.SpaceGroupResponseDto;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto.SpaceGroupGetListRes;
import com.hivey.sformservice.dto.group.SpaceGroupResponseDto.SpaceGroupListRes;

import java.util.List;

public interface SpaceGroupService {

    List<SpaceGroupGetListRes> findAllBySpaceId(Long spaceId);

    List<SpaceGroupListRes> findGroupsAndMembersBySpace(Long spaceId);

}
