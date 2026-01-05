package bed.hoc.exercice_hoc.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order/{userId}") // I've left that so you don't have to think about the path of the controller.
public class OrderController {

    @Autowired
    public OrderController() {
        //TODO it's up to you to know how to instanciate the controller now. you should know if you manage your way here!
    }

    public ResponseEntity getOrder() {
        return null;
    }

    public ResponseEntity createOrUpdate() {
        return null;
    }

    public ResponseEntity deleteOrder() {
        return null;
    }

}
