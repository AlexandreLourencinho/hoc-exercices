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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private OrderService service;
    private OrderController controller;

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

        assertThrows(UserNotFoundException.class, () -> this.controller.getOrder(1));
    }

    @Test
    void getOrderProductNotFound() {
        doThrow(new ProductNotFoundException()).when(this.service).getOrder(anyInt());

        assertThrows(ProductNotFoundException.class, () -> this.controller.getOrder(1));
    }

    @Test
    void getOrderOrderNotFound() {
        doThrow(new OrderNotFoundException()).when(this.service).getOrder(anyInt());

        assertThrows(OrderNotFoundException.class, () -> this.controller.getOrder(1));
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
        var dto = new OrderDTOUpdate();

        assertThrows(ProductNotFoundException.class, () -> this.controller.createOrUpdate(dto, 1));
    }

    @Test
    void createOrUpdateUserNotFound() {
        doThrow(new UserNotFoundException()).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));
        var dto = new OrderDTOUpdate();

        assertThrows(UserNotFoundException.class, () -> this.controller.createOrUpdate(dto, 1));
    }

    @Test
    void createOrUpdateInvalidQuantity() {
        doThrow(new InvalidQuantityException("bad qtiti")).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));
        var dto = new OrderDTOUpdate();

        assertThrows(InvalidQuantityException.class, () -> this.controller.createOrUpdate(dto, 1));
    }

    @Test
    void createOrUpdateProductInactive() {
        doThrow(new ProductInactiveException("product is inactive")).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));
        var dto = new OrderDTOUpdate();

        assertThrows(ProductInactiveException.class, () -> this.controller.createOrUpdate(dto, 1));
    }

    @Test
    void createOrUpdateStockNotSufficient() {
        doThrow(new StockNotSufficientException("not enough stock")).when(this.service).updateOrder(anyInt(), any(OrderDTOUpdate.class));
        var dto = new OrderDTOUpdate();

        assertThrows(StockNotSufficientException.class, () -> this.controller.createOrUpdate(dto, 1));
    }

    @Test
    void deleteOrder() {
        doNothing().when(this.service).deleteOrder(anyInt());

        var result = this.controller.deleteOrder(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(this.service, times(1)).deleteOrder(1);
    }

}