package com.hivey.userservice.application;

import com.hivey.userservice.dao.AuthPasswordRepository;
import com.hivey.userservice.dao.UserRepository;
import com.hivey.userservice.domain.AuthPassword;
import com.hivey.userservice.domain.User;
import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserResponseDto;
import com.hivey.userservice.dto.UserResponseDto.GetUserRes;
import com.hivey.userservice.dto.UserResponseDto.UserLoginRes;
import com.hivey.userservice.dto.UserResponseDto.UserRes;
import com.hivey.userservice.global.error.CustomException;
import com.hivey.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static com.hivey.userservice.global.config.BaseResponseStatus.*;
import static java.time.LocalDateTime.now;


@Slf4j
@Service
//final 있는 필드만 생성자 만들어줌
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AuthPasswordRepository authPasswordRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(email).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));
        log.info("userId : {}", user.getUserId());
        AuthPassword authPassword = authPasswordRepository.findByUserId(user.getUserId()).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));
        log.info("authPassword : {}", authPassword.getPassword());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), authPassword.getPassword(),
                true, true, true, true, new ArrayList<>());
    }

    /**
     * 회원가입
     * @param userRegisterRequestDto
     * @return
     */
    @Override
    public UserRes register(UserRegisterRequestDto userRegisterRequestDto) {

        // 클라이언트로부터 받은 DTO에서 email을 가진 유저 찾기
        String email = userRegisterRequestDto.getEmail();
        String password = userRegisterRequestDto.getPassword();
        Long duplicateUser = userRepository.countByEmail(email);
        log.debug("duplicateUser: {}", duplicateUser);

        try{
            // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장합니다.
            // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
            //spring security 사용해서 암호화
            userRegisterRequestDto.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new CustomException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            User registerUser = userRepository.save(UserMapper.INSTANCE.toUserFromUserRegisterEmailRequestDto(userRegisterRequestDto));
            // 유저 엔티티로부터 유저 아이디 가져오기
            Long userId = registerUser.getUserId();
            log.info("userId : {]", userId);

            AuthPassword registerPassword = authPasswordRepository.save(UserMapper.INSTANCE.toAuthPassword(userId, userRegisterRequestDto));

            String name = registerUser.getName();
            UserRes userRes = new UserRes(userId, name);
            return userRes;


        } catch (Exception exception) {
            throw new CustomException(DATABASE_ERROR);
        }

    }

    /**
     * success login
     */
    @Override
    public UserLoginRes getUserDetailsByEmail(String email) {
        User user = userRepository.findOneByEmail(email).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));
        UserLoginRes userLoginRes = new ModelMapper().map(user, UserLoginRes.class);

        return userLoginRes;

    }

    /**
     * 유저 조회
     */
    @Override
    @Transactional(readOnly = true)
    public GetUserRes getUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));

        GetUserRes getUserRes = new ModelMapper().map(user, GetUserRes.class);

        return getUserRes;
    }




}
