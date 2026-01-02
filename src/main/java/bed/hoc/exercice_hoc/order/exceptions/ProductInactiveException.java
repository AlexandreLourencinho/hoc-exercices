package bed.hoc.exercice_hoc.order.exceptions;

import bed.hoc.exercice_hoc.common.exceptions.AbstractControllerException;
import org.springframework.http.HttpStatus;

public class ProductInactiveException extends AbstractControllerException {

    private static final String ERROR_CODE = "PRODUCT_INACTIVE";
    private static final int HTTP_STATUS = HttpStatus.BAD_REQUEST.value();

    public ProductInactiveException(String message) {
        super(ERROR_CODE, HTTP_STATUS, message);
    }

}
