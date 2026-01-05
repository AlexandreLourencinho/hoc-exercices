# Level 2 – Refactoring & Exception Handling

Welcome to **Level 2** 🎯
This level is not about adding new features.

It is about **improving code quality**, **readability**, and **maintainability**.

If Level 1 was about *making the application work*,
Level 2 is about *making it clean and professional*.

---

## Main goals of this level

At this stage, the application already works... except what is expected to be refactored!

Your objectives are to:

* centralize error handling
* refactor complex business logic
* reduce duplication
* improve readability without changing behavior

⚠️ **The application must still behave exactly the same from the outside**
(all endpoints, responses and errors must remain consistent).

---

## 1️⃣ Controller refactor – Removing try/catch

In Level 1, controllers contained multiple `try / catch` blocks.

This is no longer acceptable at this level.

### What you must do

* Controllers:

    * call the service
    * return the result
    * do nothing else

Controllers no longer know **how** errors are handled.

---

## 2️⃣ Introducing ControllerAdvice

All exception handling must now be done using **ControllerAdvice**.

### Rules

* One `@RestControllerAdvice` **per package**:

    * product
    * user
    * order

* Each **business exception** must have:

    * its own `@ExceptionHandler` method
* Each handler must:

    * return a meaningful HTTP status
    * return the exception message
    * log the error

### Example

The `product` package contains **one ControllerAdvice already implemented**.

This example shows:

* how to declare a ControllerAdvice
* how to bind it to a controller
* how to handle a specific exception

⚠️ This example is **intentionally simple**.

You must:

* understand it
* adapt it
* reproduce the same logic for `user` and `order`

No additional examples are provided.

And of course, the unit tests of those ControllerAdvices must be done too. This time, I didn't left the test classes\
ready to fulfill. you'll have to create the tests from scratch entirely.

---

## 3️⃣ Order service – Major refactoring

The `updateOrder` logic in the order service has been **heavily refactored**.

### Objective

The original method in the solution of level 1 was:

* long
* difficult to read
* doing too many things at once

Maybe it was also your case.

This logic has been refactored into a **context-based approach**.

You'll see a new package in order package, the `model` package, containing the `OrderUpdateContext` class.

It's already done for you to use. see below.

If you want, you can apply that from YOUR solving of the exercise level 1. It's up to you, but It would be simplier to start from here.

---

### OrderUpdateContext

A dedicated context object is now used to group all data required for an update:

* user
* existing order (optional)
* products involved
* existing order items

This avoids:

* passing too many parameters
* re-fetching data multiple times
* deeply nested logic

The context improves:

* readability
* performance
* reasoning about the update flow

---

### Important note on performance

Lists are transformed into **maps** when frequent access by ID is required.

This avoids:

* repeated iterations
* unnecessary complexity
* hidden performance costs

Understanding **why maps are used instead of lists** is part of this level.

---

## 4️⃣ Extracting validation logic

Some logic previously embedded inside methods has been extracted into:

* dedicated private methods
* explicit assertions

Example:

* product existence validation
* duplicate checks
* preconditions

This improves:

* testability
* readability
* separation of concerns

---

## 5️⃣ User service – Duplicate checks refactoring

In Level 1, duplicate checks were handled by **two separate methods**.

In Level 2:

* they are merged into **one unified method**
* behavior is adapted based on:

    * creation
    * update
* duplication is removed
* intent is clearer

⚠️ This refactor must:

* preserve behavior
* still throw the correct business exceptions
* still respect update vs create rules

---

## 6️⃣ What you are NOT allowed to do

* Do not change endpoint paths
* Do not change request or response DTOs
* Do not modify entity mappings
* Do not change business behavior

This level is about **structure**, not features.

---

## Final note

This level is intentionally more demanding.

You will probably need to:

* read existing code carefully
* refactor step by step
* re-run tests often
* think before coding

This is normal.

Take your time — this level is designed to make you **reason**, not rush.

Good luck 💪
