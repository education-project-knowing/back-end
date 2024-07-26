package education.knowing.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class FolderRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String intro;

    @Setter
    private String createBy;

    private boolean isPublic;

    @JsonProperty("isPublic")
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic == null || isPublic;
    }
}
