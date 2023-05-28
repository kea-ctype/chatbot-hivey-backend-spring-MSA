package com.hivey.userservice.service;

import com.hivey.userservice.dao.AuthPasswordRepository;
import com.hivey.userservice.dao.UserRepository;
import com.hivey.userservice.domain.AuthPassword;
import com.hivey.userservice.domain.User;
import com.hivey.userservice.dto.UserRequestDto.UserLoginRequestDto;
import com.hivey.userservice.dto.UserRequestDto.UserRegisterRequestDto;
import com.hivey.userservice.dto.UserResponseDto.UserRes;
import com.hivey.userservice.global.error.CustomException;
import com.hivey.userservice.global.util.JwtService;
import com.hivey.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(email).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));
        AuthPassword authPassword = authPasswordRepository.findByUserId(user.getUserId()).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), authPassword.getPassword(),
                true, true, true, true, new ArrayList<>());
    }

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

            //jwt 발급.
            String jwt = jwtService.createJwt(userId); // jwt 발급
            String name = registerUser.getName();
            UserRes userRes = new UserRes(userId, name, jwt);
            return userRes;


        } catch (Exception exception) {
            throw new CustomException(DATABASE_ERROR);
        }

    }



    //로그인
//    public UserRes login(UserLoginRequestDto userLoginRequestDto) {
//        // 클라이언트로부터 받은 DTO에서 email을 가진 유저 찾기
//        String email = userLoginRequestDto.getEmail();
//        String password = userLoginRequestDto.getPassword();
//
//        log.debug("From DTO / email: {}, password: {}", email, password);
//
//        User findUser = userRepository.findOneByEmail(email).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));
//
//        // 유저 엔티티로부터 유저 아이디 가져오기
//        Long userId = findUser.getUserId();
//
//        // 해당 유저의 패스워드 가져오기
//        AuthPassword authPassword = authPasswordRepository.findByUser(findUser).orElseThrow(() -> new CustomException(FAILED_TO_LOGIN));
//
//        log.debug("From entity / email: {}, password: {}", findUser.getEmail(), authPassword.getPassword());
//
//        String decrPassword;
//        try {
//            //암호화된 패스워드 복호화 작업
//            decrPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(authPassword.getPassword());
//        } catch (Exception ignored) { // 복호화가 실패하였을 경우 에러 발생
//            throw new CustomException(PASSWORD_DECRYPTION_ERROR);
//        }
//        if (password.equals(decrPassword)) {
//            String jwtToken = jwtService.createJwt(userId);
//            String name = findUser.getName();
//            UserRes userRes = new UserRes(userId, name, jwtToken);
//            return userRes;
//        } else {
//            throw new CustomException(DATABASE_ERROR);
//        }
//    }
//
//    /**
//     * 주어진 사용자 정보가 가입된 사람인지 알려준다.
//     * FIXME: 근데 이 부분은 앞의 로그인에서 이미 처리가 되는 부분이라서 일단 구현만 해두고 연결은 안 함.
//     */
//
//    //로그아웃
//    //entity의 status 사용?
//    public UserResponseDto.UserLogoutResponseDto logout(Long userId, UserLogoutRequestDto userLogoutRequestDto){
//        // 유저 확인
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new CustomException(NOT_EXISTS_USER));
//        log.debug("from dto userId: {}",userId);
//
//        if(userId==null){
//            throw new CustomException(USERS_EMPTY_USER_ID);
//        }
//        return null;
//
//
//    }
//    @Transactional
//    public int updateUser(Long userId, UserRequestDto.UserUpdateRequestDto userUpdateRequestDto) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_EXISTS_USER));
//
//        String name=userUpdateRequestDto.getName();
//        if(name == null){
//            name = user.getName();
//        }
//
//        String img = userUpdateRequestDto.getImg();
//        if (img==null){
//            img = user.getImg();
//        }
//
//        user.userUpdate(name,img);
//
//        User updatedUser=userRepository.save(user);
//
//        if(Objects.equals(updatedUser.getUserId(), user.getUserId())){
//            return 1;
//        } else{
//            return 0;
//        }
//
//    }

}
