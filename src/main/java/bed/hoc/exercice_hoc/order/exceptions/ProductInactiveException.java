package bed.hoc.exercice_hoc.order.exceptions;

public class ProductInactiveException extends RuntimeException {

    public ProductInactiveException(String message) {
        super(message);
    }

    public ProductInactiveException(String message, String... args) {
        super(String.format(message, (Object[]) args));
    }

}
