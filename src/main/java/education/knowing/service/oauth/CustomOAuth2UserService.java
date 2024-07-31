package education.knowing.service.oauth;

import education.knowing.constant.Role;
import education.knowing.dto.user.UserDto;
import education.knowing.dto.oauth.KakaoResponse;
import education.knowing.dto.oauth.NaverResponse;
import education.knowing.dto.oauth.OAuthResponse;
import education.knowing.entity.User;
import education.knowing.entity.oauth.CustomOAuth2User;
import education.knowing.repository.UserRepository;
import education.knowing.util.RandomNicknameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthResponse oAuthResponse = null;

        if(registrationId.equals("naver")){
            oAuthResponse = new NaverResponse(oAuth2User.getAttributes());
        }else if(registrationId.equals("kakao")){
            oAuthResponse = new KakaoResponse(oAuth2User.getAttributes());
        }else {
            return null;
        }

        String username = oAuthResponse.getProvider() + " " + oAuthResponse.getProviderId();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            //닉네임을 동의하지 않을 경우 랜덤으로 닉네임 등록
            String nickname = oAuthResponse.getNickname() != null ?
                    oAuthResponse.getNickname() : RandomNicknameUtil.getRandomNickname();

            User user = User.builder()
                    .username(username)
                    .email(oAuthResponse.getEmail())
                    .nickname(nickname)
                    .role(Role.USER)
                    .build();

            userRepository.save(user);

            UserDto userDto = UserDto.builder()
                    .username(username)
                    .nickname(user.getNickname())
                    .role(user.getRole().getAuthority())
                    .build();

            return new CustomOAuth2User(userDto);
        } else{
            User existData = optionalUser.get();
            UserDto userDto = UserDto.builder()
                    .username(existData.getUsername())
                    .nickname(existData.getNickname())
                    .role(existData.getRole().getAuthority())
                    .build();

            return new CustomOAuth2User(userDto);
        }

    }
}
