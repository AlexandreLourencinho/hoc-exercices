package bed.hoc.exercice_hoc.user.exceptions;

import bed.hoc.exercice_hoc.common.exceptions.AbstractControllerException;
import org.springframework.http.HttpStatus;

public class NameAndFirstnameAlreadyExistsException extends AbstractControllerException {

    private static final String ERROR_CODE = "NAMES_TAKEN";
    private static final int HTTP_STATUS = HttpStatus.CONFLICT.value();

    public NameAndFirstnameAlreadyExistsException(String message) {
        super(ERROR_CODE, HTTP_STATUS, message);
    }

}
