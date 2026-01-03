package bed.hoc.exercice_hoc.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    // find the mapping for the get request with a path variable id. the
    public ResponseEntity getProduct() {
        return null;
    }

    @GetMapping("/stock")//here we want a path variable for the id of the product, like above. i've left the
    // getmapping because in the architecture and requests, i decided to add '/stock' in this path, and you'll have to keep it.
    // but you'll have to know how to add a pathVariable here.
    public ResponseEntity getProductStock() {
        return null;
    }

    // here we want a request param with as request a list of ids. You'll have to search how to pass request params as list
    public ResponseEntity getProducts() {
        return null;
    }

    // find the mapping for a create endpoint. it requires a CREATE DTO. check the DTO to know what's needed.
    // don't forget the @Valid annotation for the endpoint.
    public ResponseEntity createProduct() {
        return null;
    }

    // find the mapping for an update endpoint. it requires a body UPDATE dto. check the DTO to know what's needed.
    // don't forget the @Valid annotation for the endpoint.
    public ResponseEntity updateProduct() {
        return null;
    }

    //find the mapping for a delete endpoint. we're waiting for a path variable here
    public ResponseEntity<Void> deleteProduct() {
        return null;
    }

    // find how those could be passed as RequestParam through doc or stackoverflow
    public ResponseEntity<Void> deleteProducts() {
        return null;
    }

}
