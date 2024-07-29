package education.knowing.dto.question.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    @JsonProperty("fNo")
    private Long fNo;
    private String question;
    private String answer;
}
