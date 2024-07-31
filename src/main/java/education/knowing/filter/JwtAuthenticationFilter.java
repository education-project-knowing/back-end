package education.knowing.filter;

import education.knowing.dto.user.UserDto;
import education.knowing.entity.userdetails.CustomUserDetails;
import education.knowing.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        if (requestUri.matches("^/login(?:/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^/oauth2(?:/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("Authorization");

        if (accessToken == null) {
            filterChain.doFilter(request, response);

            return;
        }

        accessToken = accessToken.replace("Bearer ", "");
        //토큰 만료 여부 확인
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            //401 에러 코드, 토큰 만료
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\" : 401, \"message\" : \"Access Token 만료\"}");

            return;
        }

        //토큰 카테고리 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            //401 에러 코드, 잘못된 토큰 전달
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\" : 401, \"message\" : \"Access Token 아님\"}");

            return;
        }

        //username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        //Authentication 에 등록할 UserDetails 생성
        CustomUserDetails userDetails = new CustomUserDetails(UserDto.builder()
                .username(username)
                .role(role)
                .build());

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
