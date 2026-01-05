package bed.hoc.exercice_hoc.order.services;

import bed.hoc.exercice_hoc.order.dto.OrderDTOUpdate;
import bed.hoc.exercice_hoc.order.dto.OrderItemDTO;
import bed.hoc.exercice_hoc.order.entity.OrderEntity;
import bed.hoc.exercice_hoc.order.exceptions.InvalidQuantityException;
import bed.hoc.exercice_hoc.order.exceptions.OrderNotFoundException;
import bed.hoc.exercice_hoc.order.exceptions.ProductInactiveException;
import bed.hoc.exercice_hoc.order.exceptions.StockNotSufficientException;
import bed.hoc.exercice_hoc.order.repository.OrderRepository;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.product.services.ProductService;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import bed.hoc.exercice_hoc.user.exceptions.UserNotFoundException;
import bed.hoc.exercice_hoc.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void getOrder() {

    }

    @Test
    void getOrderOrderNotFound() {

    }

    @Test
    void getOrderUserNotFound() {

    }

    @Test
    void updateOrderUpdate() {

    }

    @Test
    void updateOrderCreate() {

    }

    @Test
    void updateOrderInvalidQuantity() {

    }

    @Test
    void updateOrderProductNotFound() {

    }

    @Test
    void updateOrderUserNotFound() {

    }

    @Test
    void updateOrderProductInactive() {
    }

    @Test
    void updateOrderStockNotSufficient() {

    }

    @Test
    void deleteOrder() {

    }

}