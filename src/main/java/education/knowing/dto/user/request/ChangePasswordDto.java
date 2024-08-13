package education.knowing.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangePasswordDto {
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&~])[A-Za-z\\d$@$!%*#?&~]{8,20}$", message = "비밀번호는 8자리 이상이여야 합니다. 영문, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;
}
