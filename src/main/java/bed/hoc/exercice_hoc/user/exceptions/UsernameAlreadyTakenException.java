package bed.hoc.exercice_hoc.user.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public UsernameAlreadyTakenException(String message) {
        super(message);
    }

}
