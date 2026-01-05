package bed.hoc.exercice_hoc.order.exceptions;

public class StockNotSufficientException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public StockNotSufficientException(String message) {
        super(message);
    }

}
