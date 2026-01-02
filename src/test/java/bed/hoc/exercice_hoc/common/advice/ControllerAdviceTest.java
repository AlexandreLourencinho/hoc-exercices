package bed.hoc.exercice_hoc.common.advice;

import bed.hoc.exercice_hoc.order.exceptions.InvalidQuantityException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerAdviceTest {

    private ControllerAdvice advice;
    private HttpServletRequest req;

    @BeforeEach
    void setup() {
        this.req = mock(HttpServletRequest.class);
        this.advice = new ControllerAdvice();
    }

    @Test
    void handleBusinessException() {
        var ex = new InvalidQuantityException("invalid qtity");
        when(this.req.getRequestURI()).thenReturn("/path/to/value");

        var result = this.advice.handleBusinessException(ex, this.req);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(400, result.getBody().getStatusCode());
        assertEquals("invalid qtity", result.getBody().getMessage());
        assertEquals("INVALID_QUANTITY", result.getBody().getErrorCode());
        assertEquals("/path/to/value", result.getBody().getPath());
        assertNotNull(result.getBody().getTimeStamp());
    }

    @Test
    void handleUnexpectedError() {
        var ex = new RuntimeException("boom");
        when(this.req.getRequestURI()).thenReturn("/path/to/value");

        var result = this.advice.handleUnexpectedError(ex, this.req);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(500, result.getBody().getStatusCode());
        assertEquals("An unexpected error occured.", result.getBody().getMessage());
        assertEquals("INTERNAL_SERVER_ERROR", result.getBody().getErrorCode());
        assertEquals("/path/to/value", result.getBody().getPath());
        assertNotNull(result.getBody().getTimeStamp());
    }

}