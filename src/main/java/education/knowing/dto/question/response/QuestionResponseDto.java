package education.knowing.dto.question.response;

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
    private Integer importance;
    private Boolean isRecognized;

    public QuestionResponseDto(Long qNo, String question, String answer) {
        this.qNo = qNo;
        this.question = question;
        this.answer = answer;
    }
}
