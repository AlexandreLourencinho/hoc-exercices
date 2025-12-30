package bed.hoc.exercice_hoc.product.services;

import bed.hoc.exercice_hoc.product.dto.ProductDTOCreate;
import bed.hoc.exercice_hoc.product.dto.ProductDTOUpdate;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository repository;
    private ProductService service;
    private ProductEntity entity;
    private ProductEntity entity2;
    private ProductDTOCreate dtoCreate;
    private ProductDTOUpdate dtoUpdate;

    @BeforeEach
    void setUp() {
        this.repository = mock(ProductRepository.class);
        this.service = new ProductServiceImpl(this.repository);
        this.entity = new ProductEntity(8, "name", "description", new BigDecimal("100.25"), 20, true);
        this.entity2 = new ProductEntity(1, "name", "description", new BigDecimal("100.25"), 20, true);
        this.dtoCreate = new ProductDTOCreate("name", "description", new BigDecimal("100.25"), 20, true);
        this.dtoUpdate = new ProductDTOUpdate(1, "name2", "description2", new BigDecimal("50.25"), 10, false);
    }

    @Test
    void getProduct() {
        when(this.repository.findById(anyInt())).thenReturn(Optional.of(this.entity));

        var result = this.service.getProduct(1);

        assertEquals(8, result.getId());
    }

    @Test
    void getProductThrows() {
        when(this.repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> this.service.getProduct(1));
    }

    @Test
    void getProductStock() {
        when(this.repository.findById(anyInt())).thenReturn(Optional.of(this.entity));

        var result = this.service.getProductStock(1);

        assertEquals(20, result.getStockQuantity());
    }

    @Test
    void getProductStockThrows() {
        when(this.repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> this.service.getProductStock(1));
    }

    @Test
    void getProducts() {
        when(this.repository.findAllById(anyList())).thenReturn(List.of(this.entity));

        var result = this.service.getProducts(List.of(8));

        assertEquals(8, result.getFirst().getId());
    }

    @Test
    void testGetProducts() {
        when(this.repository.findAll()).thenReturn(List.of(this.entity));

        var result = this.service.getProducts();

        assertEquals(8, result.getFirst().getId());
    }

    @Test
    void createProduct() {
        when(this.repository.save(any(ProductEntity.class))).thenReturn(this.entity);

        var result = this.service.createProduct(this.dtoCreate);

        assertEquals(8, result.getId());
    }

    @Test
    void updateProduct() {
        when(this.repository.findById(anyInt())).thenReturn(Optional.of(this.entity));
        when(this.repository.save(any(ProductEntity.class))).thenReturn(this.entity2);

        var result = this.service.updateProduct(this.dtoUpdate);

        assertEquals(1, result.getId());
    }

    @Test
    void updateProductThrows() {
        when(this.repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> this.service.updateProduct(this.dtoUpdate));
    }

    @Test
    void deleteProduct() {
        doNothing().when(this.repository).deleteById(anyInt());

        this.service.deleteProduct(1);

        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void deleteProducts() {
        doNothing().when(this.repository).deleteAllById(anyList());

        this.service.deleteProducts(List.of(1));

        verify(repository, times(1)).deleteAllById(List.of(1));
    }

}