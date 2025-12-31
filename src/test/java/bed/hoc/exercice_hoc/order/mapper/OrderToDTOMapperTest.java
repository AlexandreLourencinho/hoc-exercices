package bed.hoc.exercice_hoc.order.mapper;

import bed.hoc.exercice_hoc.order.entity.OrderEntity;
import bed.hoc.exercice_hoc.order.entity.OrderItemEntity;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderToDTOMapperTest {

    @Test
    void entityToDTO() {
        var user = new UserEntity();
        user.setId(1);
        var entity = new OrderEntity(1, user, new ArrayList<>());
        var product = new ProductEntity();
        product.setId(1).setStockQuantity(5).setName("name").setDescription("descr").setActive(true).setPrice(new BigDecimal("10.25"));
        var itemEntity = new OrderItemEntity(1, entity, product, 5);
        entity.getItems().add(itemEntity);

        var result = OrderToDTOMapper.entityToDTO(entity);

        assertEquals(entity.getId(), result.getId());
        assertEquals(user.getId(), result.getUserId());
    }

    @Test
    void updateEntityFromDTO() {
        var entity = new OrderEntity();
        var itemEntity = new OrderItemEntity();
        var listItem = List.of(itemEntity);

        OrderToDTOMapper.updateEntityFromDTO(entity, listItem);

        assertTrue(entity.getItems().containsAll(listItem));
    }

    @Test
    void entityItemToDTO() {
        var order = new OrderEntity();
        var product = new ProductEntity(5, "name", null, null, 5, true);
        var itemEntity = new OrderItemEntity(1, order, product, 6);

        var result = OrderToDTOMapper.entityItemToDTO(itemEntity);

        assertEquals(1, result.getId());
        assertEquals(5, result.getProductId());
        assertEquals(6, result.getQuantity());
    }

}