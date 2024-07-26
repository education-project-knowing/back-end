package education.knowing.dto.request.paging;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionPageRequestDto extends PageRequestDto{
    private String orderBy;
    private boolean isRecognized;
    private int importance;
}
