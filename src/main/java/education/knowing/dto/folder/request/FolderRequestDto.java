package education.knowing.dto.folder.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String intro;
}
