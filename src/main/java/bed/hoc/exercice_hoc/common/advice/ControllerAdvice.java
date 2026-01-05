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

//    @ExceptionHandler(XXX.class) TODO find a way to make all your business exception be catched by this exception handler. Only this one. no other method creation is allowed here.
    public ResponseEntity<ApiError> handleBusinessException() {
        return null;
    }

    @ExceptionHandler(Exception.class) // every no business exception
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
