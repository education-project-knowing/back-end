package education.knowing.dto.question.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionInfoRequestDto {
    private boolean recognized;
    private int importance;
}
