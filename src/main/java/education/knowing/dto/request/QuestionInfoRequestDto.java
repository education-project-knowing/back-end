package education.knowing.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionInfoRequestDto {
    private boolean isRecognized;
    private int importance;
}
