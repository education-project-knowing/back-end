package education.knowing.dto.email.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRequestDto {
    @NotBlank
    @Email
    private String email;

    private String certificationNumber;

    @Setter
    private String purpose;
}
