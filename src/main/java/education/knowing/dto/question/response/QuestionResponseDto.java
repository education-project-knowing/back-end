package education.knowing.dto.question.response;

import education.knowing.constant.Importance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionResponseDto {
    private Long qNo;
    private String question;
    private String answer;
    private String importance;
    private Boolean isRecognized;

    public QuestionResponseDto(Long qNo, String question, String answer) {
        this.qNo = qNo;
        this.question = question;
        this.answer = answer;
    }

    public QuestionResponseDto(Long qNo, String question, String answer, Importance importance, Boolean isRecognized) {
        this.qNo = qNo;
        this.question = question;
        this.answer = answer;
        this.importance = importance.toString();
        this.isRecognized = isRecognized;
    }
}
