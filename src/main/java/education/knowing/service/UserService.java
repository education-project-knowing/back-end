package education.knowing.service;

import education.knowing.dto.user.request.ChangePasswordDto;
import education.knowing.dto.user.response.UserInfoResponseDto;
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
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponseDto getInfo(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(BusinessError.USER_NOT_FOUND));

        return UserInfoResponseDto.from(user);
    }

    public void changeNickname(String username, String nickname){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(BusinessError.USER_NOT_FOUND));

        user.changeNickname(nickname);
    }
    public void changeEmail(String username, String email){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(BusinessError.USER_NOT_FOUND));

        user.changeEmail(email);
    }
    public void changePassword(ChangePasswordDto changePasswordDto){
        User user = userRepository.findByUsername(changePasswordDto.getUsername()).get();

        user.changePassword(passwordEncoder.encode(changePasswordDto.getPassword()));
    }

    public void withDrawn(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(BusinessError.USER_NOT_FOUND));

        user.withdrawn();
    }
}
