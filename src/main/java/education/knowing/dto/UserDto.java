package education.knowing.dto;

import education.knowing.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private String role;

    public static UserDto from(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole().getAuthority())
                .build();
    }
}
