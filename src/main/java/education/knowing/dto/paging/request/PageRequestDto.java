package education.knowing.dto.paging.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageRequestDto {
    private int page;
    private int size;
    private String keyword;
}
