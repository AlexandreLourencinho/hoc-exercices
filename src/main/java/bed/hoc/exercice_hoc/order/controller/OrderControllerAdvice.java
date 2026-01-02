package bed.hoc.exercice_hoc.order.controller;

import bed.hoc.exercice_hoc.order.exceptions.InvalidQuantityException;
import bed.hoc.exercice_hoc.order.exceptions.OrderNotFoundException;
import bed.hoc.exercice_hoc.order.exceptions.ProductInactiveException;
import bed.hoc.exercice_hoc.order.exceptions.StockNotSufficientException;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.user.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = OrderController.class)
public class OrderControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException unfex) {
        log.error("the user wasn't retrieved from database while attempting to access order", unfex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException pnfex) {
        log.error("one or more of the products weren't retrieved in database", pnfex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pnfex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException onfex) {
        log.error("The order wasn't retrieved in database", onfex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(onfex.getMessage());
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<String> handleInvalidQuantityException(InvalidQuantityException iqex) {
        log.error("The quantity of one or more product is invalid (0 or negative)", iqex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iqex.getMessage());
    }

    @ExceptionHandler(ProductInactiveException.class)
    public ResponseEntity<String> handleProductInactiveException(ProductInactiveException piex) {
        log.error("One or more of the product attempted to order is inactive", piex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(piex.getMessage());
    }

    @ExceptionHandler(StockNotSufficientException.class)
    public ResponseEntity<String> handleStockNotSufficientException(StockNotSufficientException snsex) {
        log.error("The stock of one or more of the products is insufficient to satisfy the order", snsex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(snsex.getMessage());
    }

}
