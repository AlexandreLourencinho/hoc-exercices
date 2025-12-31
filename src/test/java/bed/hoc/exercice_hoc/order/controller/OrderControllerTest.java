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

    private OrderService service;
    private OrderController controller;
    private final String userNotFound = "User not found in database";
    private final String productNotFound = "Product wasn't retrieved in database";

    @BeforeEach
    void setUp() {
        this.service = mock(OrderService.class);
        this.controller = new OrderController(this.service);
    }

    @Test
    void getOrder() {
        var itemDto = new OrderItemDTO(1, 1, 5);
        var dtoGet = new OrderDTOGet(1, 1, List.of(itemDto), new BigDecimal("50.25"));
        when(this.service.getOrder(anyInt())).thenReturn(dtoGet);

        var result = this.controller.getOrder(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dtoGet, result.getBody());
    }

    @Test
    void getOrderUserNotFound() {
        doThrow(new UserNotFoundException()).when(this.service).getOrder(anyInt());

        var result = this.controller.getOrder(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(this.userNotFound, result.getBody());
    }

    @Test
    void getOrderProductNotFound() {
        doThrow(new ProductNotFoundException()).when(this.service).getOrder(anyInt());

        var result = this.controller.getOrder(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(this.productNotFound, result.getBody());
    }

    @Test
    void getOrderOrderNotFound() {
        doThrow(new OrderNotFoundException()).when(this.service).getOrder(anyInt());

        var result = this.controller.getOrder(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        String orderNotFound = "Order wasn't retrieved from database.";
        assertEquals(orderNotFound, result.getBody());
    }

    @Test
    void createOrUpdate() {
        var itemDto = new OrderItemDTO(1, 1, 5);
        var dtoGet = new OrderDTOGet(1, 1, List.of(itemDto), new BigDecimal("50.25"));
        var dtoUpdate = new OrderDTOUpdate();
        when(this.service.updateOrder(anyInt(), any(OrderDTOUpdate.class))).thenReturn(dtoGet);

        var result = this.controller.createOrUpdate(dtoUpdate, 1);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(dtoGet, result.getBody());
    }

    @Test
    void createOrUpdateProductNotFound() {
        doThrow(new ProductNotFoundException()).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));

        var result = this.controller.createOrUpdate(new OrderDTOUpdate(), 1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(this.productNotFound, result.getBody());
    }

    @Test
    void createOrUpdateUserNotFound() {
        doThrow(new UserNotFoundException()).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));

        var result = this.controller.createOrUpdate(new OrderDTOUpdate(), 1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(this.userNotFound, result.getBody());
    }

    @Test
    void createOrUpdateInvalidQuantity() {
        doThrow(new InvalidQuantityException("bad qtiti")).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));

        var result = this.controller.createOrUpdate(new OrderDTOUpdate(), 1);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("bad qtiti", result.getBody());
    }

    @Test
    void createOrUpdateProductInactive() {
        doThrow(new ProductInactiveException("product is inactive")).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));

        var result = this.controller.createOrUpdate(new OrderDTOUpdate(), 1);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("product is inactive", result.getBody());
    }

    @Test
    void createOrUpdateStockNotSufficient() {
        doThrow(new StockNotSufficientException("not enough stock")).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));

        var result = this.controller.createOrUpdate(new OrderDTOUpdate(), 1);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("not enough stock", result.getBody());
    }

    @Test
    void deleteOrder() {
        doNothing().when(this.service).deleteOrder(anyInt());

        var result = this.controller.deleteOrder(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(this.service, times(1)).deleteOrder(1);
    }

}