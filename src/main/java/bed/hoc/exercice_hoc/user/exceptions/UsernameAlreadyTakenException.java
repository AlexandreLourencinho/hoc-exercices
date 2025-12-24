package bed.hoc.exercice_hoc.user.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
