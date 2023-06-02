package com.hivey.sformservice.application.space;

import com.hivey.sformservice.dto.member.SpaceMemberResponseDto;
import com.hivey.sformservice.dto.member.SpaceMemberResponseDto.SpaceMemberJoinRes;

public interface SpaceMemberService {
    SpaceMemberJoinRes saveSpaceMember(Long userId, String link);
}
