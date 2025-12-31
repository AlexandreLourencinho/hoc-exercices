package bed.hoc.exercice_hoc.order.services;

import bed.hoc.exercice_hoc.order.dto.OrderDTOGet;
import bed.hoc.exercice_hoc.order.dto.OrderDTOUpdate;

public interface OrderService {

    OrderDTOGet getOrder(int userId);

    OrderDTOGet updateOrder(int userId, OrderDTOUpdate dto);

    void deleteOrder(int userId);

}
