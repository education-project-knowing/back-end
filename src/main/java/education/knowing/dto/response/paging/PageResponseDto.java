package education.knowing.dto.response.paging;

import education.knowing.dto.request.paging.PageRequestDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<E> {
    private List<E> dtoList;

    private List<Integer> pageNumList;

    private boolean prev, next;

    private int page;
    private int size;
    private int prevPage;
    private int nextPage;
    private int totalPage;
    private int totalCount;
    private int current;
    @Builder
    public PageResponseDto(List<E> dtoList, PageRequestDto pageRequestDto, long totalCount) {
        this.dtoList = dtoList;
        this.page = pageRequestDto.getPage();
        this.size = pageRequestDto.getSize();
        this.totalCount = (int)totalCount;

        int end =   (int)(Math.ceil( page / 5.0 )) *  5;

        int start = end - 4;

        int last =  (int)(Math.ceil((totalCount/(double)size)));

        end = Math.min(end, last);

        this.prev = start > 1;

        this.next = this.totalCount > (end * size);

        //페이지 처리를 위해 필요한 번호
        this.pageNumList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

        if(prev) {
            this.prevPage = start -1;
        }

        if(next) {
            this.nextPage = end + 1;
        }

        this.totalPage = this.pageNumList.size();
    }
}
