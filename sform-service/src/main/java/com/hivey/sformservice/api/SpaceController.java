package com.hivey.sformservice.api;

import com.hivey.sformservice.application.space.SpaceService;
import com.hivey.sformservice.dto.space.SpaceRequestDto;
import com.hivey.sformservice.dto.space.SpaceResponseDto;
import com.hivey.sformservice.dto.space.SpaceResponseDto.SpaceCreateResponseDto;
import com.hivey.sformservice.global.config.BaseResponse;
import com.hivey.sformservice.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.hivey.sformservice.global.config.BaseResponseStatus.FAILED_TO_CREATE_SPACE;

@Slf4j
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    /**
     * 3.1 스페이스 생성하기
     */
    @PostMapping("/spaces/{userId}")
    public BaseResponse<SpaceCreateResponseDto> createSpace(@PathVariable Long userId, @Valid @RequestBody SpaceRequestDto.SpaceCreateRequestDto spaceCreateRequestDto) {



        SpaceCreateResponseDto spaceCreateResponseDto = spaceService.createSpace(userId, spaceCreateRequestDto);

        if (spaceCreateResponseDto != null) {
            // 요청에 성공한 경우
            return new BaseResponse<>(spaceCreateResponseDto);
        } else {
            // 요청에 실패한 경우
            throw new CustomException(FAILED_TO_CREATE_SPACE);
        }
    }





}
