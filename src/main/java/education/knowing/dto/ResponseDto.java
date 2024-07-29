package education.knowing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResponseDto<T> {
    private int status_code;
    private String message;
    private T data;

    public ResponseDto(int status_code, String message) {
        this.status_code = status_code;
        this.message = message;
    }
}
