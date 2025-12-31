package bed.hoc.exercice_hoc.order.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Order wasn't retrieved from database.");
    }

}
