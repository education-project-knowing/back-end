package education.knowing.dto.folder.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FolderResponseDto {
    private Long fno;
    private String title;
    private String intro;
    private String createBy;
    private Long totalQuestionCount;
    private Long recognizeQuestionCount;
    private boolean isPublic;

    //로그인 X
    public FolderResponseDto(Long fno, String title, String intro, String createBy, Long totalQuestionCount, boolean isPublic) {
        this.fno = fno;
        this.title = title;
        this.intro = intro;
        this.createBy = createBy;
        this.totalQuestionCount = totalQuestionCount;
        this.recognizeQuestionCount = 0L;
        this.isPublic = isPublic;
    }

    //로그인 O
    public FolderResponseDto(Long fno, String title, String intro, String createBy, Long totalQuestionCount, Long recognizeQuestionCount, boolean isPublic) {
        this.fno = fno;
        this.title = title;
        this.intro = intro;
        this.createBy = createBy;
        this.totalQuestionCount = totalQuestionCount;
        this.recognizeQuestionCount = recognizeQuestionCount;
        this.isPublic = isPublic;
    }
}
