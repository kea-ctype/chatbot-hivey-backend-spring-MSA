package com.hivey.sformservice.application.space;

import com.hivey.sformservice.dto.space.SpaceRequestDto.SpaceCreateReq;
import com.hivey.sformservice.dto.space.SpaceResponseDto;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceCreateRes;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceInfoRes;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceListRes;

import java.util.List;

public interface SpaceService {

    SpaceCreateRes createSpace(Long userId, SpaceCreateReq spaceCreateRequestDto);
    SpaceInfoRes getSpace(Long spaceId, Long userId);
    List<SpaceListRes> findAllByUserId(Long userId);
}
