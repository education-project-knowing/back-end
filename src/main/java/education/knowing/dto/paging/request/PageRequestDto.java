package education.knowing.dto.paging.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageRequestDto {
    private int page = 1;
    private int size = 10;
    private String keyword;
}
