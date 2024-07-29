package education.knowing.dto.question.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private Long fNo;
    private String question;
    private String answer;
}
