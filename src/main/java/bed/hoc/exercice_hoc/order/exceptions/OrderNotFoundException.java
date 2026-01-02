package bed.hoc.exercice_hoc.order.exceptions;

import bed.hoc.exercice_hoc.common.exceptions.AbstractControllerException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends AbstractControllerException {

    private static final String ERROR_CODE = "ORDER_NOT_FOUND";
    private static final int HTTP_STATUS = HttpStatus.NOT_FOUND.value();

    public OrderNotFoundException(String message) {
        super(ERROR_CODE, HTTP_STATUS, message);
    }

}
