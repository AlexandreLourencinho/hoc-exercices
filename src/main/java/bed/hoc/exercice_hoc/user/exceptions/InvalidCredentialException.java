package bed.hoc.exercice_hoc.user.exceptions;

public class InvalidCredentialException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public InvalidCredentialException() {
        super("The password is incorrect");
    }

}
