package bed.hoc.exercice_hoc.user.exceptions;

public class UserNotFoundException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public UserNotFoundException(String message) {
        super(message);
    }

}
