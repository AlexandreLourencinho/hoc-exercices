package bed.hoc.exercice_hoc.order.exceptions;

import bed.hoc.exercice_hoc.common.exceptions.AbstractControllerException;
import org.springframework.http.HttpStatus;

public class StockNotSufficientException extends AbstractControllerException {

    private static final String ERROR_CODE = "STOCK_NOT_SUFFICIENT";
    private static final int HTTP_STATUS = HttpStatus.CONFLICT.value();

    public StockNotSufficientException(String message) {
        super(ERROR_CODE, HTTP_STATUS, message);
    }

}
