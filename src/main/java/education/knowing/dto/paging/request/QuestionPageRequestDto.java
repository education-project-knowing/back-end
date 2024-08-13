package education.knowing.dto.paging.request;

import education.knowing.constant.Importance;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionPageRequestDto extends PageRequestDto{
    private String orderBy = "createdDate";
    private Boolean recognized;
    private List<Importance> importance;
}
