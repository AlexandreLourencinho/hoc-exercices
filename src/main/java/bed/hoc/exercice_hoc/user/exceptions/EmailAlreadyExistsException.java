package bed.hoc.exercice_hoc.user.exceptions;

import bed.hoc.exercice_hoc.common.exceptions.AbstractControllerException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends AbstractControllerException {

    private static final String ERROR_CODE = "EMAIL_TAKEN";
    private static final int HTTP_STATUS = HttpStatus.CONFLICT.value();

    public EmailAlreadyExistsException(String message) {
        super(ERROR_CODE, HTTP_STATUS, message);
    }

}
