package bed.hoc.exercice_hoc.order.controller;

import bed.hoc.exercice_hoc.order.dto.OrderDTOGet;
import bed.hoc.exercice_hoc.order.dto.OrderDTOUpdate;
import bed.hoc.exercice_hoc.order.dto.OrderItemDTO;
import bed.hoc.exercice_hoc.order.exceptions.InvalidQuantityException;
import bed.hoc.exercice_hoc.order.exceptions.OrderNotFoundException;
import bed.hoc.exercice_hoc.order.exceptions.ProductInactiveException;
import bed.hoc.exercice_hoc.order.exceptions.StockNotSufficientException;
import bed.hoc.exercice_hoc.order.services.OrderService;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getOrder() {
    }

    @Test
    void getOrderUserNotFound() {
    }

    @Test
    void getOrderProductNotFound() {

    }

    @Test
    void getOrderOrderNotFound() {

    }

    @Test
    void createOrUpdate() {

    }

    @Test
    void createOrUpdateProductNotFound() {

    }

    @Test
    void createOrUpdateUserNotFound() {

    }

    @Test
    void createOrUpdateInvalidQuantity() {

    }

    @Test
    void createOrUpdateProductInactive() {

    }

    @Test
    void createOrUpdateStockNotSufficient() {

    }

    @Test
    void deleteOrder() {

    }

}