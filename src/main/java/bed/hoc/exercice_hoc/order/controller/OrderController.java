package bed.hoc.exercice_hoc.order.controller;

import bed.hoc.exercice_hoc.order.dto.OrderDTOGet;
import bed.hoc.exercice_hoc.order.dto.OrderDTOUpdate;
import bed.hoc.exercice_hoc.order.services.OrderService;
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
    public ResponseEntity<OrderDTOGet> getOrder(@PathVariable int userId) {
        return ResponseEntity.ok(this.service.getOrder(userId));
    }

    @PutMapping
    public ResponseEntity<OrderDTOGet> createOrUpdate(@RequestBody @Valid OrderDTOUpdate dto, @PathVariable int userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.updateOrder(userId, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(@PathVariable int userId) {
        this.service.deleteOrder(userId);
        return ResponseEntity.noContent().build();
    }

}
