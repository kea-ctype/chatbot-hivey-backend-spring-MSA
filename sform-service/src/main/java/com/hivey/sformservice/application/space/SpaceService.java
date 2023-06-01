package com.hivey.sformservice.application.space;

import com.hivey.sformservice.dto.space.SpaceRequestDto.SpaceCreateRequestDto;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceCreateResponseDto;

public interface SpaceService {

    SpaceCreateResponseDto createSpace(Long userId, SpaceCreateRequestDto spaceCreateRequestDto);
}
