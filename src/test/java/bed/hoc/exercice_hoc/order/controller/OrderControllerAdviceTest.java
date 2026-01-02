package bed.hoc.exercice_hoc.order.controller;

import bed.hoc.exercice_hoc.order.exceptions.InvalidQuantityException;
import bed.hoc.exercice_hoc.order.exceptions.OrderNotFoundException;
import bed.hoc.exercice_hoc.order.exceptions.ProductInactiveException;
import bed.hoc.exercice_hoc.order.exceptions.StockNotSufficientException;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderControllerAdviceTest {

    private OrderControllerAdvice advice;

    @BeforeEach
    void setUp() {
        this.advice = new OrderControllerAdvice();
    }

    @Test
    void handleUserNotFoundException() {
        var unfex = new UserNotFoundException();

        var result = this.advice.handleUserNotFoundException(unfex);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found in database", result.getBody());
    }

    @Test
    void handleProductNotFoundException() {
        var pnfex = new ProductNotFoundException();

        var result = this.advice.handleProductNotFoundException(pnfex);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Product wasn't retrieved in database", result.getBody());
    }

    @Test
    void handleOrderNotFoundException() {
        var onfex = new OrderNotFoundException();

        var result = this.advice.handleOrderNotFoundException(onfex);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Order wasn't retrieved from database.", result.getBody());
    }

    @Test
    void handleInvalidQuantityException() {
        var iqex = new InvalidQuantityException("boomed quantity");

        var result = this.advice.handleInvalidQuantityException(iqex);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("boomed quantity", result.getBody());
    }

    @Test
    void handleProductInactiveException() {
        var piex = new ProductInactiveException("boomed inactive");

        var result = this.advice.handleProductInactiveException(piex);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("boomed inactive", result.getBody());
    }

    @Test
    void handleStockNotSufficientException() {
        var snsex = new StockNotSufficientException("boomed stock");

        var result = this.advice.handleStockNotSufficientException(snsex);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("boomed stock", result.getBody());
    }

}