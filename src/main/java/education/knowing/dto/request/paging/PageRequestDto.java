package education.knowing.dto.request.paging;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageRequestDto {
    private int page;
    private int size;
    private String keyword;
}
