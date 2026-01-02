package bed.hoc.exercice_hoc.user.exceptions;

import bed.hoc.exercice_hoc.common.exceptions.AbstractControllerException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialException extends AbstractControllerException {

    private static final String ERROR_CODE = "BAD_CREDENTIALS";
    private static final int HTTP_STATUS = HttpStatus.UNAUTHORIZED.value();

    public InvalidCredentialException() {
        super(ERROR_CODE, HTTP_STATUS, "The password is incorrect");
    }

}
