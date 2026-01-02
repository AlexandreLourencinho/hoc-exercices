package bed.hoc.exercice_hoc.product.controller;

import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = ProductController.class)
public class ProductControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException pnfex) {
        log.error("the requested product wasn't retrieved from database", pnfex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pnfex.getMessage());
    }

}
