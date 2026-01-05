package bed.hoc.exercice_hoc.order.exceptions;

public class ProductInactiveException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public ProductInactiveException(String message) {
        super(message);
    }

}
