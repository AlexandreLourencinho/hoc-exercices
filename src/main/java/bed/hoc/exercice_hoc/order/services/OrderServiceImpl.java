package bed.hoc.exercice_hoc.order.services;

import bed.hoc.exercice_hoc.order.dto.OrderDTOGet;
import bed.hoc.exercice_hoc.order.dto.OrderDTOUpdate;
import bed.hoc.exercice_hoc.order.dto.OrderItemDTO;
import bed.hoc.exercice_hoc.order.entity.OrderEntity;
import bed.hoc.exercice_hoc.order.entity.OrderItemEntity;
import bed.hoc.exercice_hoc.order.exceptions.InvalidQuantityException;
import bed.hoc.exercice_hoc.order.exceptions.OrderNotFoundException;
import bed.hoc.exercice_hoc.order.exceptions.ProductInactiveException;
import bed.hoc.exercice_hoc.order.exceptions.StockNotSufficientException;
import bed.hoc.exercice_hoc.order.mapper.OrderToDTOMapper;
import bed.hoc.exercice_hoc.order.model.OrderUpdateContext;
import bed.hoc.exercice_hoc.order.repository.OrderRepository;
import bed.hoc.exercice_hoc.product.entity.ProductEntity;
import bed.hoc.exercice_hoc.product.exceptions.ProductNotFoundException;
import bed.hoc.exercice_hoc.product.services.ProductService;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import bed.hoc.exercice_hoc.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, UserService userService, ProductService productService) {
        this.repository = repository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public OrderDTOGet getOrder(int userId) {
        var user = this.userService.getUserEntity(userId);
        var order = this.repository.findByUser(user)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order wasn't retrieved for user %s", user.getUsername())));
        return OrderToDTOMapper.entityToDTO(order);
    }

    @Override
    @Transactional
    public OrderDTOGet updateOrder(int userId, OrderDTOUpdate dto) {

        this.validateQuantities(dto);

        var context = this.buildUpdateContext(userId, dto);

        // check if all products are found in db and if the stock is sufficient to add to the order
        // theoretically this check should be done once in the front already, but it'll be done in the back also anyway
        dto.getItems().forEach(item -> this.checkDtoValidity(item, context.getExistingItemsMap(), context.getProductMap()));

        //same as before with the optional. or the order is here, and so we update it, or we create it, here in the
        // orElseGet(() -> ) is called when optional is empty, and so we create a new Entity.
        OrderEntity orderEntity = context.getExistingOrder().map(entity -> this.updateOrderItems(entity, dto, context.getProductMap()))
                .orElseGet(() -> this.createOrder(context.getUser(), dto, context.getProductMap()));
        return OrderToDTOMapper.entityToDTO(this.repository.save(orderEntity));
    }

    @Override
    @Transactional
    public void deleteOrder(int userId) {
        var user = this.userService.getUserEntity(userId);
        this.repository.deleteByUser(user);
    }

    /**
     * builds the context of an order update
     * @param userId an integer for the id of the user
     * @param dto an {@link OrderDTOUpdate} used to create the context
     * @return a {@link OrderUpdateContext} that will represent all needed information of the order
     */
    private OrderUpdateContext buildUpdateContext(int userId, OrderDTOUpdate dto) {

        var user = userService.getUserEntity(userId);

        //get products asked from user. the map here uses the list of items in dto and returns a list with only the ids.
        var products = productService.getProductsEntity(
                dto.getItems().stream()
                        .map(OrderItemDTO::getProductId)
                        .toList()
        );

        // transformed into a map for performance. Collectors.toMap() works like that :
        // the first parameter will be the key. since it's a list of product entities, we want ids in key.
        // the second one is the entity itself. Since it needs a function, we take the product as parameter and return it directly.
        // that's why the second parameter is p -> p.
        var productMap = products.stream()
                .collect(Collectors.toMap(ProductEntity::getId, p -> p));// stock products in a map with id in key and product entity in value

        var existingOrder = repository.findByUser(user);

        // transforming the list into map for performance gain. map.get() is much faster than iterating over a list.
        // the order here is an optional. so there's two cases:
        // order.map() consider it "present". so it creates the map of existing items.
        // the 'orElse' here returns an empty map, because otherwise the variable would be null.
        // and if the variable is null, existingItemsMap.get() in checkDtoValidity would return a null pointer.
        var existingItemsMap = existingOrder
                .map(o -> o.getItems().stream()
                        .collect(Collectors.toMap(
                                oi -> oi.getProduct().getId(),
                                oi -> oi
                        )))
                .orElse(Collections.emptyMap());

        return new OrderUpdateContext(
                user,
                existingOrder,
                productMap,
                existingItemsMap
        );
    }

    /**
     * centralized method to check the dto validity
     * @param item the {@link OrderItemDTO} containing the product id to be checked
     * @param existingItemsMap a map of {@link OrderItemEntity} that will be checked
     * @param productMap a map of {@link ProductEntity} that will be added to the order
     */
    private void checkDtoValidity(OrderItemDTO item, Map<Integer, OrderItemEntity> existingItemsMap, Map<Integer, ProductEntity> productMap) {
        var product = productMap.get(item.getProductId());
        if (product == null)
            throw new ProductNotFoundException(String.format("Product with id %s wasn't retrieved in database", item.getProductId().toString()));

        this.validateProduct(product);

        int delta = this.computeStockDelta(item, existingItemsMap);

        //check stock availability
        if (product.getStockQuantity() - delta < 0) {
            throw new StockNotSufficientException(
                    String.format("there isn't enough stock for the product %s %s", product.getId(), product.getName()));
        }

        // decrease or readjust stock from delta
        product.setStockQuantity(product.getStockQuantity() - delta);
    }

    /**
     * validate that all quantities set up for products are valid (a.k.a. > 0)
     * @param dto the {@link OrderDTOUpdate} containing the {@link OrderItemDTO} list to check
     */
    private void validateQuantities(OrderDTOUpdate dto) {
        if (dto.getItems().stream().anyMatch(predicate -> predicate.getQuantity() <= 0)) {
            var itemInvalid = dto.getItems().stream().filter(predicate -> predicate.getQuantity() <= 0).findFirst().orElse(null);
            throw new InvalidQuantityException(
                    itemInvalid != null ?
                            String.format("Product %s has a invalid quantity of %s", itemInvalid.getProductId().toString(), itemInvalid.getQuantity().toString())
                            : "A product was set with an invalid quantity."
            );
        }
    }

    /**
     * compare and extract the existing delta in stock when updating a product quantity
     * @param item the {@link OrderItemDTO} containing the product id and quantity to check
     * @param existingItemsMap a map with {@link OrderItemEntity} as value to gather to existing product quantity
     * @return an int >=0, 0 when the product wasn't already present in the order previously
     */
    private int computeStockDelta(OrderItemDTO item, Map<Integer, OrderItemEntity> existingItemsMap) {
        var existingItem = existingItemsMap.get(item.getProductId());
        return item.getQuantity() - (existingItem != null ? existingItem.getQuantity() : 0);
    }

    /**
     * Check if the product that is being added to the order is Active
     * @param product a {@link ProductEntity}
     */
    private void validateProduct(ProductEntity product) {
        if (!product.isActive()) {
            throw new ProductInactiveException(
                    String.format("the product %s (%s) is inactive", product.getId(), product.getName()));
        }
    }

    /**
     * create a new {@link OrderEntity} based on information from the dto
     * @param user the {@link UserEntity} that passes the command
     * @param dto the {@link OrderDTOUpdate} send by the request to create the order
     * @param productMap a map of {@link ProductEntity} to be added to the order
     * @return an {@link OrderEntity} that will be saved in database
     */
    private OrderEntity createOrder(UserEntity user, OrderDTOUpdate dto, Map<Integer, ProductEntity> productMap) {
        var orderEntity = new OrderEntity();
        orderEntity.setUser(user);
        var listOrderItemEntity = dto.getItems().stream().map(item ->
                new OrderItemEntity(null, orderEntity, productMap.get(item.getProductId()), item.getQuantity())
        ).toList();
        OrderToDTOMapper.updateEntityFromDTO(orderEntity, listOrderItemEntity);
        return orderEntity;
    }

    /**
     * Centralized method for updating an existing order
     * @param orderEntity the {@link OrderEntity} to be updated
     * @param dto the {@link OrderDTOUpdate} used for the update coming from the request
     * @param productMap a map of {@link ProductEntity} containing the product to add or update in the order
     * @return the updated {@link OrderEntity}
     */
    private OrderEntity updateOrderItems(OrderEntity orderEntity, OrderDTOUpdate dto, Map<Integer, ProductEntity> productMap) {
        // transforming the list into map for performance gain. map.get() is lighter to user than foreach-ing a list
        // or doing .filter().findFirst() or things like that.
        Map<Integer, OrderItemEntity> existing =
                orderEntity.getItems().stream()
                        .collect(Collectors.toMap(oi -> oi.getProduct().getId(), i -> i));

        List<OrderItemEntity> updated = new ArrayList<>();

        dto.getItems().forEach(dtoItem -> {
            OrderItemEntity entity;

            // if the id is not null AND the OrderItemEntity was already present in the order previously
            if (dtoItem.getId() != null && existing.containsKey(dtoItem.getProductId())) {
                entity = existing.remove(dtoItem.getProductId());
                entity.setQuantity(dtoItem.getQuantity());
            } else {
                // OR : the product wasn't there in the order. we create a new OrderItemEntity that will be added
                entity = new OrderItemEntity(null,
                        orderEntity,
                        productMap.get(dtoItem.getProductId()),
                        dtoItem.getQuantity()
                );
            }
            updated.add(entity);
        });
        // note that the mapper doesn't do a setitems(). it clears the same list and then do a .addAll()
        // it's because the jpa entity has to still has the same list (same memory allocation) to persist correctly.
        //Otherwise, it would be considered as a "new list" / "other list" and could cause problems of persistence in db.
        OrderToDTOMapper.updateEntityFromDTO(orderEntity, updated);
        return orderEntity;
    }

}
