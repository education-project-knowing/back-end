package education.knowing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CertificationDto {
    @NotBlank
    @Email
    private String email;

    private String certificationNumber;
}
