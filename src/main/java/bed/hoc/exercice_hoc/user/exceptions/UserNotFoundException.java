package bed.hoc.exercice_hoc.user.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found in database");
    }
}
