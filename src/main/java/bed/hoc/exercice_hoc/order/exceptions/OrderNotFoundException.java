package bed.hoc.exercice_hoc.order.exceptions;

public class OrderNotFoundException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public OrderNotFoundException(String message) {
        super(message);
    }

}
