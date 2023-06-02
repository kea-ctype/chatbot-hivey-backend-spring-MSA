package com.hivey.sformservice.application.space;

import com.hivey.sformservice.dto.space.SpaceRequestDto.SpaceCreateReq;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceCreateRes;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceInfoRes;

public interface SpaceService {

    SpaceCreateRes createSpace(Long userId, SpaceCreateReq spaceCreateRequestDto);
    SpaceInfoRes getSpace(Long spaceId, Long userId);
}
