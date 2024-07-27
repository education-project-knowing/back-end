package education.knowing.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import education.knowing.entity.Opinion;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpinionResponseDto {
    private Long opinionId;
    private String writer;
    private String opinion;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;

    public static OpinionResponseDto from(Opinion opinion){
        return OpinionResponseDto.builder()
                .opinionId(opinion.getOpinionId())
                .writer(opinion.getCreatedBy())
                .createDate(opinion.getCreatedDate())
                .opinion(opinion.getOpinion())
                .build();
    }
}
