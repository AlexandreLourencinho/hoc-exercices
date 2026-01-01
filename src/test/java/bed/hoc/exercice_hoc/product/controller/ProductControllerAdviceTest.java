package bed.hoc.exercice_hoc.product.controller;

import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerAdviceTest {

    @Test
    void handleProductNotFoundException() {
        var advice = new ProductControllerAdvice();
        var ex = new ProductNotFoundException();

        var result = advice.handleProductNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Product wasn't retrieved in database", result.getBody());
    }

}