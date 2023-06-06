package com.hivey.sformservice.api.space;

import com.hivey.sformservice.application.space.SpaceMemberService;
import com.hivey.sformservice.application.space.SpaceService;
import com.hivey.sformservice.dto.member.SpaceMemberRequestDto.SpaceMemberJoinReq;
import com.hivey.sformservice.dto.member.SpaceMemberResponseDto.SpaceMemberJoinRes;
import com.hivey.sformservice.dto.space.SpaceRequestDto.SpaceCreateReq;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceCreateRes;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceInfoRes;
import com.hivey.sformservice.global.config.BaseResponse;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.hivey.sformservice.dto.space.SpaceResponseDto.*;
import static com.hivey.sformservice.global.config.BaseResponseStatus.FAILED_TO_CREATE_SPACE;

@Slf4j
@RestController
@RequestMapping("/spaces")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;
    private final SpaceMemberService spaceMemberService;

    /**
     * 3.1 스페이스 생성하기
     */
    @PostMapping("/{userId}")
    public BaseResponse<SpaceCreateRes> createSpace(@PathVariable Long userId, @Valid @RequestBody SpaceCreateReq spaceCreateRequestDto) {
        SpaceCreateRes spaceCreateRes = spaceService.createSpace(userId, spaceCreateRequestDto);

        if (spaceCreateRes != null) {
            // 요청에 성공한 경우
            return new BaseResponse<>(spaceCreateRes);
        } else {
            // 요청에 실패한 경우
            throw new CustomException(FAILED_TO_CREATE_SPACE);
        }
    }

    /**
     * 3.2 스페이스 조회하기
     */
    @GetMapping("/{spaceId}/{userId}")
    public BaseResponse<SpaceInfoRes> getSpaceInformation(@PathVariable Long spaceId, @PathVariable Long userId) {

        SpaceInfoRes spaceInfoRes = spaceService.getSpace(spaceId, userId);

        return new BaseResponse<>(spaceInfoRes);
    }

    /**
     * 3.3 스페이스 참여하기
     */
    @PostMapping("/{userId}/members")
    public BaseResponse<SpaceMemberJoinRes> joinSpace(@PathVariable Long userId, @Valid @RequestBody SpaceMemberJoinReq spaceMemberJoinReq) {

        String accessCode = spaceMemberJoinReq.getAccessCode();

        return new BaseResponse<>(spaceMemberService.saveSpaceMember(userId, accessCode));
    }

    /**
     * 3.4 참여한 스페이스 목록 불러오기
     */
    @GetMapping("/{userId}")
    public BaseResponse<List<SpaceListRes>> getJoinedSpaces(@PathVariable Long userId) {
        return new BaseResponse<>(spaceService.findAllByUserId(userId));
    }




}
