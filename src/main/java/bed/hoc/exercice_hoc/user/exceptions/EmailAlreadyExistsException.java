package bed.hoc.exercice_hoc.user.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

}
