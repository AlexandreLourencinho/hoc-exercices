package bed.hoc.exercice_hoc.user.exceptions;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super("The password is incorrect");
    }
}
