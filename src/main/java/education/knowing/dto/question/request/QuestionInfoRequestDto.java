package education.knowing.dto.question.request;

import education.knowing.constant.Importance;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionInfoRequestDto {
    private boolean recognized;
    private Importance importance;
}
