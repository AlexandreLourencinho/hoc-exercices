package bed.hoc.exercice_hoc.product.exceptions;

public class ProductNotFoundException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public ProductNotFoundException() {
        super("Product wasn't retrieved in database");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
