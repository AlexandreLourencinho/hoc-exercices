package bed.hoc.exercice_hoc.order.exceptions;

public class InvalidQuantityException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public InvalidQuantityException(String message) {
        super(message);
    }

}
