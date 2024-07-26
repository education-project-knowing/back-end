package education.knowing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {
    private Long qNo;
    private String question;
    private String answer;
    private int importance;
    private boolean isRecognized;

    public QuestionResponseDto(Long qNo, String question, String answer) {
        this.qNo = qNo;
        this.question = question;
        this.answer = answer;
    }
}
