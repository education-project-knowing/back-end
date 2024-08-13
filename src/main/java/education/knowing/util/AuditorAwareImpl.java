package education.knowing.util;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

//현재 로그인한 사용자의 정보를 등록자와 수정자로 지정하기 위해 AuditorAware 인터페이스 구현
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        if(authentication != null){
            username = authentication.getName();
        }
        return Optional.of(username);
    }
}
