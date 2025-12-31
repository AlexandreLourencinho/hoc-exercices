package bed.hoc.exercice_hoc.order.exceptions;

public class StockNotSufficientException extends RuntimeException {

    public StockNotSufficientException(String message) {
        super(message);
    }

}
