package bed.hoc.exercice_hoc.common.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public abstract class AbstractControllerException extends RuntimeException {

    // TODO This class will be central to Level 3. Think about how to use it.

    private final String errorCode;
    private final int statusCode;

    protected AbstractControllerException(String errorCode, int statusCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

}
