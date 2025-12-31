package bed.hoc.exercice_hoc.order.controller;

import bed.hoc.exercice_hoc.order.dto.OrderDTOUpdate;
import bed.hoc.exercice_hoc.order.exceptions.InvalidQuantityException;
import bed.hoc.exercice_hoc.order.exceptions.OrderNotFoundException;
import bed.hoc.exercice_hoc.order.exceptions.ProductInactiveException;
import bed.hoc.exercice_hoc.order.exceptions.StockNotSufficientException;
import bed.hoc.exercice_hoc.order.services.OrderService;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.user.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/{userId}")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity getOrder(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(this.service.getOrder(userId));
        } catch (UserNotFoundException | ProductNotFoundException | OrderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity createOrUpdate(@RequestBody @Valid OrderDTOUpdate dto, @PathVariable int userId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.updateOrder(userId, dto));
        } catch (ProductNotFoundException | UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InvalidQuantityException | ProductInactiveException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (StockNotSufficientException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity deleteOrder(@PathVariable int userId) {
        this.service.deleteOrder(userId);
        return ResponseEntity.noContent().build();
    }

}
