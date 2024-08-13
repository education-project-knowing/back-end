package education.knowing.dto.email.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindPasswordRequestDto extends CertificationRequestDto{
    @NotBlank
    private String id;
}
