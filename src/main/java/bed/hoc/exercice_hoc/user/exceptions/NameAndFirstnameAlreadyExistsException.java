package bed.hoc.exercice_hoc.user.exceptions;

public class NameAndFirstnameAlreadyExistsException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public NameAndFirstnameAlreadyExistsException(String message) {
        super(message);
    }

}
