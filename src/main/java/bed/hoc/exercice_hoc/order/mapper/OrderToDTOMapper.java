package bed.hoc.exercice_hoc.order.mapper;

import bed.hoc.exercice_hoc.common.constants.CommonConstants;
import bed.hoc.exercice_hoc.order.dto.OrderDTOGet;
import bed.hoc.exercice_hoc.order.dto.OrderItemDTO;
import bed.hoc.exercice_hoc.order.entity.OrderEntity;
import bed.hoc.exercice_hoc.order.entity.OrderItemEntity;

import java.util.List;

public class OrderToDTOMapper {

    private OrderToDTOMapper() {
        CommonConstants.throwUtilityClassException();
    }

    public static OrderDTOGet entityToDTO(OrderEntity entity) {
        return new OrderDTOGet(entity.getId(),
                entity.getUser().getId(),
                entity.getItems().stream().map(OrderToDTOMapper::entityItemToDTO).toList(),
                entity.getTotalPrice());
    }

    public static void updateEntityFromDTO(OrderEntity entity, List<OrderItemEntity> items) {
        entity.getItems().clear();
        entity.getItems().addAll(items); // clear and addall instead of set to avoid the lost of persistance through jpa
    }

    public static OrderItemDTO entityItemToDTO(OrderItemEntity entity) {
        return new OrderItemDTO(entity.getId(), entity.getProduct().getId(), entity.getQuantity());
    }

}
