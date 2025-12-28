package bed.hoc.exercice_hoc.product.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Product wasn't retrieved in database");
    }

}
