# Order package — Final exercise (Level 1)

## ⚠️ Important: this is the final and most complex part of the exercise

The `order` package is the **last step** of this project and represents the **highest level of difficulty** for level 1.

Unlike `product` and `user`:

* **Almost nothing is implemented**
* You must build the **controller**, **service**, **mapper**, and **business logic** yourself
* This package **depends on both `user` and `product`**

If you reached this point, you should already be familiar with:

* controllers structure
* services and business rules
* repositories
* DTOs vs entities
* exception handling
* unit tests

Take your time.
This package is intentionally designed to make you think.

---

## Core business rule: 1 user = 1 order

A **user can have only one order**.

In a real-world application, a cart and an order are usually two distinct concepts.
A cart represents a temporary selection of products, while an order represents a finalized purchase.

In this exercise, the Order is intentionally designed as a mix between a cart and an order, to avoid duplicating very similar business logic and to keep the focus on core concepts (relationships, stock management, transactions).

This explains:

* the controller path structure
* the absence of `orderId` in most endpoints
* why creating and updating an order are merged into a single operation

The controller base path is:

```
/order/{userId}
```

This path has been intentionally left for you so you don’t have to design it.

---

## Controller

The controller is intentionally **incomplete**.

```java
@RestController
@RequestMapping("/order/{userId}")
public class OrderController { 
    //....
}
```

What you must do:

* inject the required dependencies
* define the HTTP methods, parameters and bodies
* return proper `ResponseEntity`
* rely on the service layer for business logic

💡 At this point, you are expected to know:

* how to inject services
* how to bind path variables and request bodies
* how to map HTTP verbs to actions

---

## Entities: why two entities?

### OrderEntity

Represents an order made by a user.

Key points:

* linked to a `UserEntity`
* contains a list of order items
* computes its total price dynamically

```java
@ManyToOne
@JoinColumn(name = "user_id", nullable = false)
private UserEntity user;
```

### OrderItemEntity

Represents **one product inside an order**, with a quantity.

This entity exists because:

* an order can contain multiple products
* a product can appear in multiple orders
* a quantity must be stored per product per order

➡️ This is a **many-to-many relationship** between `Order` and `Product`, resolved with an intermediate entity.

---

## DTOs

DTOs are already defined.
You must **use them as-is**.

### OrderDTOGet

Returned when retrieving an order.

Contains:

* order id
* user id
* list of items
* total price

### OrderDTOUpdate

Used to create or update an order.

Important:

* only contains items
* the user id is already provided by the controller path
* remember: **1 user = 1 order**

### OrderItemDTO

Represents a product inside an order.

Contains:

* product id
* quantity

Validation annotations are already present.

---

## Exceptions

Specific order exceptions:

* `OrderNotFoundException`
* `InvalidQuantityException`
* `StockNotSufficientException`
* `ProductInactiveException`

⚠️ Important:
You will **also need exceptions from other packages** (`user`, `product`).

Understanding:

* **which exception to throw**
* **when**
* **and why**

is part of the exercise.

Nothing is explicitly wired for you here on purpose.

---

## Mapper

The mapper is intentionally incomplete.

Key points to pay attention to:

### Entity → DTO

You must:

* map items
* compute total price using the entity logic
* expose only what is needed

### Updating entity collections

⚠️ Very important:

When updating the order items:

* **DO NOT replace the list**
* **DO NOT use `entity.setItems()`**

JPA persistence requires keeping the **same list instance**.

You must:

* clear the existing list
* add new elements to it

This is a very common real-world pitfall.

---

## Service

The service is where **most of the difficulty lies**.

Everything is to be implemented.

### getOrder

* retrieve the order for a given user
* throw `OrderNotFoundException` if none exists
* return the mapped DTO

### updateOrder

This method handles **both creation and update**.

You must:

* validate the DTO
* retrieve or create the order
* manage order items
* manage stock updates
* return the updated order

⚠️ Stock management is critical.

You must **never double subtract stock**.

Example:

* previous quantity: 15
* new quantity: 16
* stock must be reduced by **1**, not 16

This requires computing the **delta** between old and new quantities.

### deleteOrder

* delete the user’s order

ℹ️ Note:
Stock refill on deletion is **not implemented in the solution**.
This is left as an optional improvement exercise.

---

## Transactions

The `@Transactional` annotation has been left intentionally.

You may:

* understand why it is needed
* keep it
* or remove it if you manage consistency differently

This is part of the learning process.

---

## Final words

This package is:

* complex
* interconnected
* intentionally under-guided

That is **by design**.

If you can complete this package correctly, you:

* understand CRUD + business rules
* understand JPA pitfalls
* understand transactional logic
* are ready to move beyond beginner-level Spring Boot

Good luck.
And remember: struggling here means you’re learning. 💪
