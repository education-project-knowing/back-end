package education.knowing.service.jwt;

import education.knowing.dto.user.UserDto;
import education.knowing.entity.userdetails.CustomUserDetails;
import education.knowing.entity.User;
import education.knowing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 아이디"));

        return new CustomUserDetails(UserDto.from(user));
    }

}
