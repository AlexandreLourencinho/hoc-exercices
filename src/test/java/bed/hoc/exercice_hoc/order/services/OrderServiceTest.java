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

    private OrderRepository repository;
    private UserService userService;
    private ProductService productService;
    private OrderService service;

    @BeforeEach
    void setUp() {
        this.repository = mock(OrderRepository.class);
        this.userService = mock(UserService.class);
        this.productService = mock(ProductService.class);
        this.service = new OrderServiceImpl(this.repository, this.userService, this.productService);
    }

    @Test
    void getOrder() {
        var user = new UserEntity();
        var order = new OrderEntity();
        order.setUser(user).setId(55);
        when(this.userService.getUserEntity(anyInt())).thenReturn(user);
        when(this.repository.findByUser(any(UserEntity.class))).thenReturn(Optional.of(order));

        var result = this.service.getOrder(1);

        assertEquals(55, result.getId());
    }

    @Test
    void getOrderOrderNotFound() {
        var user = new UserEntity();
        var order = new OrderEntity();
        order.setUser(user).setId(55);
        when(this.userService.getUserEntity(anyInt())).thenReturn(user);
        doThrow(new OrderNotFoundException()).when(this.repository).findByUser(any(UserEntity.class));

        assertThrows(OrderNotFoundException.class, () -> this.service.getOrder(1));
    }

    @Test
    void getOrderUserNotFound() {
        doThrow(new UserNotFoundException()).when(this.userService).getUserEntity(anyInt());

        assertThrows(UserNotFoundException.class, () -> this.service.getOrder(1));
    }

    @Test
    void updateOrderUpdate() {
        var dto = new OrderDTOUpdate();
        var user = new UserEntity(1, null, null, null, null, null);
        var itemDto = new OrderItemDTO(1, 8, 5);
        var productEntity = new ProductEntity(8, null, null, new BigDecimal("88.99"), 10, true);
        var order = new OrderEntity();
        order.setId(1).setUser(user);
        itemDto.setQuantity(5).setId(1).setProductId(8);
        dto.setItems(List.of(itemDto));
        when(this.productService.getProductsEntity(anyList())).thenReturn(List.of(productEntity));
        when(this.userService.getUserEntity(anyInt())).thenReturn(user);
        when(this.repository.findByUser(any(UserEntity.class))).thenReturn(Optional.of(order));
        when(this.repository.save(any(OrderEntity.class))).thenReturn(order);

        var result = this.service.updateOrder(1, dto);

        assertEquals(1, result.getId());
    }

    @Test
    void updateOrderCreate() {
        var dto = new OrderDTOUpdate();
        var user = new UserEntity(1, null, null, null, null, null);
        var itemDto = new OrderItemDTO(1, 8, 5);
        var productEntity = new ProductEntity(8, null, null, null, 10, true);
        var order = new OrderEntity();
        order.setId(1).setUser(user);
        itemDto.setQuantity(5).setId(1).setProductId(8);
        dto.setItems(List.of(itemDto));
        when(this.productService.getProductsEntity(anyList())).thenReturn(List.of(productEntity));
        when(this.userService.getUserEntity(anyInt())).thenReturn(user);
        when(this.repository.findByUser(any(UserEntity.class))).thenReturn(Optional.empty());
        when(this.repository.save(any(OrderEntity.class))).thenReturn(order);

        var result = this.service.updateOrder(1, dto);

        assertEquals(1, result.getId());
    }

    @Test
    void updateOrderInvalidQuantity() {
        var dto = new OrderDTOUpdate();
        var itemDto = new OrderItemDTO();
        itemDto.setQuantity(-5);
        dto.setItems(List.of(itemDto));

        assertThrows(InvalidQuantityException.class, () -> this.service.updateOrder(1, dto));
    }

    @Test
    void updateOrderProductNotFound() {
        var dto = new OrderDTOUpdate();
        var itemDto = new OrderItemDTO();
        itemDto.setQuantity(5);
        dto.setItems(List.of(itemDto));
        doThrow(new ProductNotFoundException()).when(this.productService).getProductsEntity(anyList());

        assertThrows(ProductNotFoundException.class, () -> this.service.updateOrder(1, dto));
    }

    @Test
    void updateOrderUserNotFound() {
        var dto = new OrderDTOUpdate();
        var itemDto = new OrderItemDTO();
        var productEntity = new ProductEntity(8, null, null, null, null, true);
        itemDto.setQuantity(5).setId(1).setProductId(2);
        dto.setItems(List.of(itemDto));
        when(this.productService.getProductsEntity(anyList())).thenReturn(List.of(productEntity));
        doThrow(new UserNotFoundException()).when(this.userService).getUserEntity(anyInt());

        assertThrows(UserNotFoundException.class, () -> this.service.updateOrder(1, dto));
    }

    @Test
    void updateOrderProductInactive() {
        var dto = new OrderDTOUpdate();
        var itemDto = new OrderItemDTO();
        var productEntity = new ProductEntity(8, null, null, null, null, false);
        itemDto.setQuantity(5).setId(1).setProductId(8);
        dto.setItems(List.of(itemDto));
        when(this.productService.getProductsEntity(anyList())).thenReturn(List.of(productEntity));
        when(this.repository.findByUser(any(UserEntity.class))).thenReturn(Optional.empty());

        assertThrows(ProductInactiveException.class, () -> this.service.updateOrder(1, dto));
    }

    @Test
    void updateOrderStockNotSufficient() {
        var dto = new OrderDTOUpdate();
        var itemDto = new OrderItemDTO();
        var productEntity = new ProductEntity(8, null, null, null, 1, true);
        itemDto.setQuantity(5).setId(1).setProductId(8);
        dto.setItems(List.of(itemDto));
        when(this.productService.getProductsEntity(anyList())).thenReturn(List.of(productEntity));
        when(this.repository.findByUser(any(UserEntity.class))).thenReturn(Optional.empty());

        assertThrows(StockNotSufficientException.class, () -> this.service.updateOrder(1, dto));
    }

    @Test
    void deleteOrder() {
        var user = new UserEntity();
        when(this.userService.getUserEntity(anyInt())).thenReturn(user);
        doNothing().when(this.repository).deleteByUser(any(UserEntity.class));

        this.service.deleteOrder(1);

        verify(this.repository, times(1)).deleteByUser(user);
    }

}