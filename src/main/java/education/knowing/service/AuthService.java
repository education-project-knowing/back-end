package education.knowing.service;

import education.knowing.constant.Role;
import education.knowing.dto.auth.request.SignUpRequestDto;
import education.knowing.dto.auth.response.SignUpResponseDto;
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

    public SignUpResponseDto join(SignUpRequestDto userDto) {
        //중복 체크
        if (idCheck(userDto.getUsername())) {
            throw new BusinessLogicException(BusinessError.DUPLICATED_ID);
        }
        if (emailCheck(userDto.getEmail())) {
            throw new BusinessLogicException(BusinessError.DUPLICATED_EMAIL);
        }
        if (nicknameCheck(userDto.getNickname())) {
            throw new BusinessLogicException(BusinessError.DUPLICATED_NICKNAME);
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .role(Role.USER)
                .build();

        User result = userRepository.save(user);

        return new SignUpResponseDto(result.getId());
    }

    @Transactional(readOnly = true)
    public boolean idCheck(String id) {
        return userRepository.existsByUsername(id);
    }

    @Transactional(readOnly = true)
    public boolean emailCheck(String email) {
        return userRepository.existsByEmail(email);

    }

    @Transactional(readOnly = true)
    public boolean nicknameCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }


}
