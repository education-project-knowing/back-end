package education.knowing.advice;

import education.knowing.dto.response.ResponseDto;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ResponseDto<Object>> handleBusinessLogicException(BusinessLogicException e) {
        BusinessError businessError = e.getBusinessError();
        ResponseDto<Object> responseDto = new ResponseDto<>(businessError.getStatus(), businessError.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(e.getBusinessError().getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        ResponseDto<Object> responseDTO = ResponseDto.builder()
                .status_code(400)
                .message("사용자 입력 검증 오류 발생")
                .data(errors)
                .build();
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseDto<Object>> databaseException(BusinessLogicException e) {
        BusinessError businessError = e.getBusinessError();
        ResponseDto<Object> responseDto = new ResponseDto<>(500, "데이터베이스 에러");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }
}
