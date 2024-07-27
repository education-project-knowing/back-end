package education.knowing.handler;

import education.knowing.entity.oauth.CustomOAuth2User;
import education.knowing.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String username = customOAuth2User.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt("refresh", username, role, 24*60*60*1000L);

        //쿠키로는 refresh 토큰만 전달 후 access token 받는 과정 추가
        response.addCookie(createCookie("refresh", token));
        response.sendRedirect("http://localhost:3000");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //보일 위치 - 전역
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //cookie.setSecure(true);

        return cookie;
    }
}