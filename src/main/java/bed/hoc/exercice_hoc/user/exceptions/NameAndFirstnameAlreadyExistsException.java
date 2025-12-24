package bed.hoc.exercice_hoc.user.exceptions;

public class NameAndFirstnameAlreadyExistsException extends RuntimeException {
    public NameAndFirstnameAlreadyExistsException(String message) {
        super(message);
    }
}
