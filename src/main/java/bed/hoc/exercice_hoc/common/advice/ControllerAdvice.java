package bed.hoc.exercice_hoc.common.advice;

import bed.hoc.exercice_hoc.common.exceptions.AbstractControllerException;
import bed.hoc.exercice_hoc.common.model.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(AbstractControllerException.class)
    public ResponseEntity<ApiError> handleBusinessException(AbstractControllerException ex, HttpServletRequest req) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatusCode())
                .body(new ApiError(ex.getStatusCode(),
                        ex.getMessage(),
                        req.getRequestURI(),
                        ex.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedError(Exception ex, HttpServletRequest req) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiError(500,
                        "An unexpected error occured.",
                        req.getRequestURI(),
                        "INTERNAL_SERVER_ERROR")
        );
    }

}
