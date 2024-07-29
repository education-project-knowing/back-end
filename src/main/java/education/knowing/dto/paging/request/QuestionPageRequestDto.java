package education.knowing.dto.paging.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionPageRequestDto extends PageRequestDto{
    private String orderBy = "createdDate";
    private Boolean recognized;
    private List<Integer> importance;
}
