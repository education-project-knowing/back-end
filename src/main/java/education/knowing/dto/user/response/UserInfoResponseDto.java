package education.knowing.dto.user.response;

import education.knowing.entity.User;
import lombok.Builder;

@Builder
public class UserInfoResponseDto {
    private String username;
    private String nickname;
    private String email;

    public static UserInfoResponseDto from(User user){
        return UserInfoResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
