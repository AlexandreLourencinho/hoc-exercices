# Product Package – Level 1 Exercise

This package is the **first one you should work on**.
It is intentionally the **simplest** package of the project and serves as an introduction to the expected patterns.

In the **product package**:

* ✅ the **service is already implemented**
* ❌ the **controller must be implemented**
* ❌ the **mapper must be completed**
* ❌ the **repository must be fixed**
* ❌ **unit tests must be written**

---

## General rules for this package

### Pay close attention to the Entity and the DTOs

This is the **main source of truth** to understand:

* what is stored in the database
* what is sent to the frontend (responses)
* what is received from the frontend (requests)

Always compare:

* Entity ↔ DTOCreate
* Entity ↔ DTOUpdate
* Entity ↔ DTOGet / DTOStock

---

### Do not forget unit tests

This package contains:

* a controller
* a mapper

Both **must be tested**.

The test classes already exist and the test methods are already created.
Your job is to **implement the tests** so that they match what the method names describe.

---

## Important: the project will NOT compile at first

### The repository is intentionally broken

Since this is normally the first package you will work on, the repository has been intentionally left incomplete.

Your task is to:

* transform it into a real JPA repository

This is a simple operation:

* an annotation is missing
* the interface must extend the correct JPA interface

You can check the **user repository** to see how it is done.

Until this is fixed, **the project will not compile**.

---

### The service unit tests will fail at first

Even after fixing the repository, the service tests will still fail **until the mapper is fully implemented**.

This behavior is **intentional**.
Do not be surprised if nothing works at first.

---

## Controller

This is where **most of your work will be**, along with the mapper.

### Pay attention to:

* return types
* expected parameters

Depending on the endpoint, parameters may come from:

* path variables
* request parameters
* request body
* or nothing at all

Your goal is to connect the controller to the **already implemented service**, so that:

* all requests from the Postman collection
  `resources/postman/manual_tests`
  related to products **work correctly**

### Endpoint base path

All product endpoints are available under:

```
localhost:8080/product
```
---

### You have an example in the user package

The **user controller is already implemented**.

You can use it as a reference to:

* understand mappings
* see how parameters are handled
* see how responses are returned

However, you are encouraged to:

* analyze each endpoint
* determine by yourself which annotations and parameters are needed

---

### About AI usage

AI tools like ChatGPT are fully capable of implementing this controller alone.

However, the goal of this exercise is for you to:

* understand which service methods to call
* understand what each endpoint returns
* understand how to wire everything together

Take your time to reason before coding.

---

### Return types must NOT be changed

All controller methods are expected to return:

```
ResponseEntity
```

Without generics (`<>`).

This is **intentional** and must not be changed.

---

### Paths must NOT be changed

The base paths and mappings must remain unchanged.

You may add **path parameters** where required.
Path parameters **do not count** as changing the path.

Comments inside the controller indicate:

* what each endpoint is supposed to do
* whether it expects parameters or not

You must determine:

* which mapping annotation to use
* which parameters to declare

Again, the user controller is a reference.

---

### Exception handling is mandatory

You must manage exceptions using **try / catch blocks**.

To do this correctly:

* analyze the product service
* identify which methods can throw which exceptions

Rules:

* catch **only** the exceptions defined in the exception package
* do **not** catch `Exception` or `RuntimeException`

Catching generic exceptions would hide potential bugs, which is not desired.

---

## Controller – Methods description

### `getProduct`

Retrieves a single product.
Returns a `ProductDTOGet`.

---

### `getProductStock`

Retrieves the stock of a specific product.
Returns a `ProductDTOStock`.

⚠️ Pay special attention to the **path** for this endpoint.
If you change the path, you must update the Postman requests accordingly.

Adding a path parameter does **not** count as changing the path.

---

### `getProducts`

Returns a list of products.

This endpoint can:

* return all products
* or return a subset based on a list of IDs

You must determine how to:

* check if IDs are provided
* call the correct service method accordingly

---

### `createProduct`

Creates a new product.

* Expects a `ProductDTOCreate`
* Returns a `ProductDTOGet`
* This is a **create** endpoint, so the HTTP method must differ from the previous ones

Do not forget to:

* use `@Valid` on the request body

Check the user controller or the documentation for reference.

---

### `updateProduct`

Updates an existing product.

* Expects a `ProductDTOUpdate`
* Returns a `ProductDTOGet`
* This is an **update** endpoint, so the HTTP method must differ from the others

---

### `deleteProduct`

Deletes a single product.

This is a **delete** endpoint, so the HTTP method must differ from the others.

---

### `deleteProducts`

Deletes multiple products.

* Expects a request parameter containing a list of IDs

---

## DTO package

### `ProductDTOCreate`

Sent by the frontend (or Postman) to create a product.

---

### `ProductDTOUpdate`

Sent by the frontend (or Postman) to update a product.

---

### `ProductDTOGet`

Returned by the backend to the frontend / request.

---

### `ProductDTOStock`

Returned by the backend when querying the stock of a specific product.

---

## Entity package

There are no specific business rules on this entity, except for the ID.

* The ID is an **auto-incremented integer**
* Auto-incremented IDs are generated by JPA when saving the entity

⚠️ When creating a new entity:

* the ID field **must be null**
* otherwise JPA will not generate it correctly

More complex business rules will be found in:

* the user package
* and especially in the order package

---

## Exception package

There is only **one business exception** for products.

It is thrown when a product cannot be retrieved:

* during a get
* or during an update

---

## Mapper package

The mapper is responsible for converting:

* entities ↔ DTOs

One method (`dtoUpdateToEntity`) is already implemented as an example.

The other methods are empty and must be implemented.

---

### Mapper methods

#### `entityToDTOGet`

Returns a `ProductDTOGet` from a `ProductEntity`.

---

#### `dtoCreateToEntity`

Creates a new `ProductEntity` from a `ProductDTOCreate`.

⚠️ Reminder:

* the ID must be left `null`
* JPA will generate it automatically when saving

---

#### `entityToDTOStock`

Returns a `ProductDTOStock` from an existing entity.

---

#### `dtoUpdateToEntity`

Already implemented.

This method shows how updates are expected to be done:

* all setters are called, even if values did not change

This is **intentional**.
Updating a field with the same value does not affect JPA behavior, as long as the ID is not changed.

---

## Repository package

No custom repository methods are required for this exercise.

The repository is currently **just an interface**.

You must:

* annotate it correctly
* extend the appropriate JPA repository interface

You can check the user repository for reference.

---

## Service package

The product service is **fully implemented**.

You can:

* analyze it freely
* use it to implement the controller
* use it as a reference for other services

### Important note

The service will **not work correctly** until the mapper is fully implemented.

Service unit tests will fail until the mapper is completed.
