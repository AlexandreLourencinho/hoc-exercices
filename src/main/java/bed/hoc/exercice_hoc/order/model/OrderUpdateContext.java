package bed.hoc.exercice_hoc.order.model;

import bed.hoc.exercice_hoc.order.entity.OrderEntity;
import bed.hoc.exercice_hoc.order.entity.OrderItemEntity;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderUpdateContext {

    private UserEntity user;
    private Optional<OrderEntity> existingOrder; // we accept optional as field at this level for clarity of the code, despite the warning
    private Map<Integer, ProductEntity> productMap;
    private Map<Integer, OrderItemEntity> existingItemsMap;

}
