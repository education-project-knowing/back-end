package education.knowing.advice;

import education.knowing.dto.response.ResponseDto;
import education.knowing.exception.BusinessError;
import education.knowing.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ResponseDto<Object>> handleBusinessLogicException(BusinessLogicException e) {
        BusinessError businessError = e.getBusinessError();
        ResponseDto<Object> responseDto = new ResponseDto<>(businessError.getStatus(), businessError.getMessage(), null);
        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(e.getBusinessError().getStatus()));
    }
}
