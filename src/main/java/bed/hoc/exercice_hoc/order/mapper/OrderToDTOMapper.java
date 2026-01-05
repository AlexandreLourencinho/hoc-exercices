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
        return null;
    }

    public static void updateEntityFromDTO(OrderEntity entity, List<OrderItemEntity> items) {
        //beware of how you update the list of entity. you have to not use entity.set() here, since the persistence
        // needs to be kept, and so the SAME list (memory allocation) should be kept.
        // up to you to search and find how to clear a list and add all elements of another list.
    }

    public static OrderItemDTO entityItemToDTO(OrderItemEntity entity) {
        return null;
    }

}
