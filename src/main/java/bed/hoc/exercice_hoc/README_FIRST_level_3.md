# Level 3 – Centralized Exception Handling & Refactoring

Welcome to **Level 3** 🎯
This level is about **centralizing exception handling**, **refactoring your validation logic**, and **making your code cleaner and more modular**.

If Level 2 was about *splitting responsibilities and improving structure*, Level 3 is about *making exception handling unified and business rules explicit*.

---

## Main Goals

Your objectives for this level are:

* Centralize exception handling in one place
* Separate validation logic into dedicated methods
* Use the provided `UserIdentity` record in user-related checks
* Maintain the same behavior as Level 2
* Ensure **no business exception returns a 500 INTERNAL SERVER ERROR**

⚠️ **The application must behave the same externally.**
Endpoints, responses, and error messages may change format, but HTTP statuses for business errors must be appropriate.

---

## 1️⃣ Centralized ControllerAdvice

In Level 2, each package had its own `ControllerAdvice` and one handler per business exception.

Now:

* You should **centralize all exception handling** in a single `ControllerAdvice` class in the `common` package
* One method should handle **all business exceptions**
* Another method should handle **all unexpected errors**

### Example (incomplete)

```java
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    // TODO implement a method to catch all business exceptions
    public ResponseEntity<ApiError> handleBusinessException() {
        return null;
    }

    @ExceptionHandler(Exception.class) // all non-business exceptions
    public ResponseEntity<ApiError> handleUnexpectedError(Exception ex, HttpServletRequest req) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiError(500,
                        "An unexpected error occurred.",
                        req.getRequestURI(),
                        "INTERNAL_SERVER_ERROR")
        );
    }
}
```

> **Hint:** Use the abstract exception provided to unify your business exceptions.

---

## 2️⃣ AbstractControllerException & Business Exceptions

All business exceptions should now be related to `AbstractControllerException`.

```java
@Getter
@Setter
@Accessors(chain = true)
public abstract class AbstractControllerException extends RuntimeException {
    private final String errorCode;
    private final int statusCode;

    protected AbstractControllerException(String errorCode, int statusCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }
}
```

For each business exception:

```java
public class EmailAlreadyExistsException extends RuntimeException {

    // TODO adapt this exception to work with the centralized ControllerAdvice.

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
```

> Think carefully about how these exceptions could share behavior and structure.

---

## 3️⃣ OrderService – Refactoring Validation

Several methods that previously contained all validation logic in one place have now been **split into dedicated private methods**.

Example placeholders:

```java
private void validateQuantities(OrderDTOUpdate dto) {
    // TODO check that all quantities in the DTO are valid (>0)
}

private int computeStockDelta(OrderItemDTO item, Map<Integer, OrderItemEntity> existingItemsMap) {
    // TODO compute the difference between new and existing quantities
    return 0;
}

private void validateProduct(ProductEntity product) {
    // TODO check that the product exists and is active
}
```

> Each method should do **one clear task**. Your code should be easier to read and maintain than Level 2.

---

## 4️⃣ UserService – Using UserIdentity

The `UserIdentity` record is provided for checking duplicates:

```java
public record UserIdentity(Integer id, String email, String username, String name, String firstname) {}
```

You should use it when implementing duplicate checks:

```java
private void checkDuplicateFields(UserIdentity identity, String action) {
    // TODO implement checks for email, username, and name+firstname
}
```

> Split the logic clearly between creation and update, and ensure correct exceptions are thrown.
> Avoid duplicating code and keep your logic modular.

---

## 5️⃣ ApiError

`ApiError` is provided as the response object for exceptions:

```java
@Getter
@Setter
@Accessors(chain = true)
public class ApiError {
    private final Instant timeStamp = Instant.now();
    private int statusCode;
    private String message;
    private String path;
    private String errorCode;

    public ApiError(int statusCode, String message, String path, String errorCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
    }
}
```

> You may extend or adapt this as needed, but your ControllerAdvice should use it for all errors.

---

## 6️⃣ Notes & Recommendations

* No business exception should return **500 INTERNAL SERVER ERROR**
* Keep validation logic **separate and modular**
* Use the `UserIdentity` record for all user duplicate checks
* Centralize exception handling but think carefully about **HOW TO SHARE BEHAVIOR BETWEEN EXCEPTION**
* You can refer to Level 2 for general refactoring patterns, but **do not reuse old ControllerAdvices** and **DO NOT ADD ANY OTHER CONTROLLER ADVICE OR ANY OTHER METHOD IN THE EXISTING CONTROLLER ADVICE**.

Take your time. This level is about reasoning, structuring, and modularizing your code.

Good luck !
