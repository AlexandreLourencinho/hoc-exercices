package bed.hoc.exercice_hoc.order.services;

import bed.hoc.exercice_hoc.order.dto.OrderDTOGet;
import bed.hoc.exercice_hoc.order.dto.OrderDTOUpdate;
import bed.hoc.exercice_hoc.order.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    public OrderServiceImpl() {
        //TODO you'll see what you need in your controller here!
    }

    @Override
    @Transactional // i've left the tansactional annotation because it's something i've needed during the development of the solution. you'll see if you need it or not.
    public OrderDTOGet getOrder(int userId) {
        return null;
    }

    @Override
    @Transactional
    public OrderDTOGet updateOrder(int userId, OrderDTOUpdate dto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteOrder(int userId) {
        // important note on delete:
        // i didn't implement the REFILL of the stock when you DELETE an order.
        // it could be an interesting exercice for you to implement that missing part of the solution all by yourself.
    }

    private void checkDtoValidity() {
        //TODO
    }

    private OrderEntity createOrder() {
        return null;
    }

    private OrderEntity updateOrderItems() {
        //important note on update.
        // you'll have to manage the stocks. BE CAREFUL to not DOUBLE SUBTRACT the quantity.
        //for example: you had an order of product id 1 with 15 as quantity.
        // the stock of product 1 was 20. so there's 5 left.
        // if you update your order and set 16 instead of 15, the new quantity must be calculated from the delta
        // between the old quantity and the new one. otherwise, in this case, you'll ask for another 16 items;
        // and checking the stock will be 5 - 16 instead of the real change in quantity, a.k.a. 5 - 1 (difference between 16, the new quantity, and 15, the old one)
        return null;
    }

}
