package education.knowing.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IdCheckRequestDto {
    @NotBlank(message = "아이디를 입력하세요.")
    @Pattern(regexp = "^[a-z0-9]{6,19}$", message = "아이디는 영어 소문자와 숫자만 사용하여 6~19자리여야 합니다.")
    String username;
}
