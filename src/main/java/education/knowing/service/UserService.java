package education.knowing.service;

import education.knowing.dto.ResponseDto;
import education.knowing.dto.user.request.ChangePasswordDto;
import education.knowing.entity.User;
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

    public ResponseDto<?> changePassword(ChangePasswordDto changePasswordDto){
        User user = userRepository.findByUsername(changePasswordDto.getUsername()).get();

        user.changePassword(passwordEncoder.encode(changePasswordDto.getPassword()));

        return new ResponseDto<>(200, "비밀번호 변경");
    }
}
