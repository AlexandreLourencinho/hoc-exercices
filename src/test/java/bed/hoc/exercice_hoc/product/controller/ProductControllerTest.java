package bed.hoc.exercice_hoc.product.controller;

import bed.hoc.exercice_hoc.product.dto.ProductDTOCreate;
import bed.hoc.exercice_hoc.product.dto.ProductDTOGet;
import bed.hoc.exercice_hoc.product.dto.ProductDTOStock;
import bed.hoc.exercice_hoc.product.dto.ProductDTOUpdate;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.product.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductService service;
    private ProductController controller;
    private ProductDTOGet dtoGet;
    private ProductDTOCreate dtoCreate;
    private ProductDTOUpdate dtoUpdate;
    private ProductDTOStock dtoStock;
    private final String messageNotFound = "Product wasn't retrieved in database";

    @BeforeEach
    void setUp() {
        this.dtoGet = new ProductDTOGet(1, "name", "description", new BigDecimal("100.25"), 20, true);
        this.dtoCreate = new ProductDTOCreate("name", "description", new BigDecimal("100.25"), 20, true);
        this.dtoUpdate = new ProductDTOUpdate(1, "name2", "description2", new BigDecimal("50.25"), 10, false);
        this.dtoStock = new ProductDTOStock(15);
        this.service = mock(ProductService.class);
        this.controller = new ProductController(this.service);
    }

    @Test
    void getProduct() {
        when(this.service.getProduct(anyInt())).thenReturn(this.dtoGet);

        var result = this.controller.getProduct(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(this.dtoGet, result.getBody());
    }

    @Test
    void getProductNotFound() {
        doThrow(new ProductNotFoundException()).when(this.service).getProduct(anyInt());

        var result = this.controller.getProduct(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(this.messageNotFound, result.getBody());
    }

    @Test
    void getProductStock() {
        when(this.service.getProductStock(anyInt())).thenReturn(this.dtoStock);

        var result = this.controller.getProductStock(20);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(this.dtoStock, result.getBody());
    }

    @Test
    void getProductStockNotFound() {
        doThrow(new ProductNotFoundException()).when(this.service).getProductStock(anyInt());

        var result = this.controller.getProductStock(20);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(this.messageNotFound, result.getBody());
    }

    @Test
    void getProducts() {
        when(this.service.getProducts()).thenReturn(List.of(this.dtoGet));

        var result = this.controller.getProducts(null);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(this.dtoGet, ((List<ProductDTOGet>) result.getBody()).getFirst());
    }

    @Test
    void getProductsWithIds() {
        when(this.service.getProducts(anyList())).thenReturn(List.of(this.dtoGet));

        var result = this.controller.getProducts(List.of(1));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(this.dtoGet, ((List<ProductDTOGet>) result.getBody()).getFirst());
    }

    @Test
    void createProduct() {
        when(this.service.createProduct(any(ProductDTOCreate.class))).thenReturn(this.dtoGet);

        var result = this.controller.createProduct(this.dtoCreate);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(this.dtoGet, result.getBody());
    }

    @Test
    void updateProduct() {
        when(this.service.updateProduct(any(ProductDTOUpdate.class))).thenReturn(this.dtoGet);

        var result = this.controller.updateProduct(this.dtoUpdate);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(this.dtoGet, result.getBody());
    }

    @Test
    void updateProductNotFound() {
        doThrow(new ProductNotFoundException()).when(this.service).updateProduct(any(ProductDTOUpdate.class));

        var result = this.controller.updateProduct(this.dtoUpdate);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(this.messageNotFound, result.getBody());
    }

    @Test
    void deleteProduct() {
        doNothing().when(this.service).deleteProduct(anyInt());

        var result = this.controller.deleteProduct(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void deleteProducts() {
        doNothing().when(this.service).deleteProducts(anyList());

        var result = this.controller.deleteProducts(List.of(1));

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}