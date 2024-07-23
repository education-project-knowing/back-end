package education.knowing.service;

import education.knowing.constant.Role;
import education.knowing.dto.UserDto;
import education.knowing.dto.response.ResponseDto;
import education.knowing.entity.User;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import education.knowing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ResponseDto<Object> join(UserDto userDto){
        //중복 체크
        idCheck(userDto.getUsername());
        emailCheck(userDto.getEmail());
        nicknameCheck(userDto.getNickname());

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .role(Role.USER)
                .build();

        User result = userRepository.save(user);

        return new ResponseDto<>(201, "회원가입 성공", null);
    }

    @Transactional(readOnly = true)
    public ResponseDto<Object> idCheck(String id){
        if(userRepository.existsByUsername(id)){
            throw new BusinessLogicException(BusinessError.DUPLICATED_ID);
        }
        return new ResponseDto<>(200, "사용 가능한 아이디", null);
    }
    @Transactional(readOnly = true)
    public ResponseDto<Object> emailCheck(String email){
        if(userRepository.existsByEmail(email)){
            throw new BusinessLogicException(BusinessError.DUPLICATED_EMAIL);
        }
        return new ResponseDto<>(200, "사용 가능한 이메일", null);
    }
    @Transactional(readOnly = true)
    public ResponseDto<Object> nicknameCheck(String nickname){
        if(userRepository.existsByNickname(nickname)){
            throw new BusinessLogicException(BusinessError.DUPLICATED_NICKNAME);
        }
        return new ResponseDto<>(200, "사용 가능한 닉네임", null);
    }
}
