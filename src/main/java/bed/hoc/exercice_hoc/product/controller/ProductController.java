package bed.hoc.exercice_hoc.product.controller;

import bed.hoc.exercice_hoc.product.dto.ProductDTOCreate;
import bed.hoc.exercice_hoc.product.dto.ProductDTOUpdate;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.product.services.ProductService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity getProduct(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.service.getProduct(id));
        } catch (ProductNotFoundException pnfex) {
            return this.manageCatchedError(pnfex);
        }
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity getProductStock(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.service.getProductStock(id));
        } catch (ProductNotFoundException pnfex) {
            return this.manageCatchedError(pnfex);
        }
    }

    @GetMapping
    public ResponseEntity getProducts(@RequestParam @Nullable List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            return ResponseEntity.ok(this.service.getProducts(ids));
        } else {
            return ResponseEntity.ok(this.service.getProducts());
        }
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody @Valid ProductDTOCreate dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.createProduct(dto));
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody @Valid ProductDTOUpdate dto) {
        try {
            return ResponseEntity.ok(this.service.updateProduct(dto));
        } catch (ProductNotFoundException pnfex) {
            return this.manageCatchedError(pnfex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        this.service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProducts(@RequestParam List<Integer> ids) {
        this.service.deleteProducts(ids);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<String> manageCatchedError(ProductNotFoundException pnex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pnex.getMessage());
    }

}
