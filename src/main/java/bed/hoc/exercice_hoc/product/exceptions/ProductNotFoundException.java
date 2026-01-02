package bed.hoc.exercice_hoc.product.exceptions;

import bed.hoc.exercice_hoc.common.exceptions.AbstractControllerException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends AbstractControllerException {

    private static final String ERROR_CODE = "PRODUCT_NOT_FOUND";
    private static final int HTTP_STATUS = HttpStatus.NOT_FOUND.value();

    public ProductNotFoundException() {
        super(ERROR_CODE, HTTP_STATUS, "Product wasn't retrieved in database");
    }

    public ProductNotFoundException(String message) {
        super(ERROR_CODE, HTTP_STATUS, message);
    }

}
