# Hello there 👋
## This is the exercise project you've been waiting for!

Before starting, there are a few important rules to follow.

---

### 1 - Read every markdown file carefully
Every markdown `readme.md` or `readme_something.md` file (like this one) must be read thoroughly.

Every line is written for a reason.  
Do not skip anything.

---

### 2 - Start with the `product` package
This is the simplest package and the one where the most guidance has been left.

There are also many indications in the `user` package, but it is slightly more complex than `product`.

Finally, the project won't compile until the product package is fixed (the repository) and the unit tests won't pass\
until you finish the mapper of the product package.

This is intentional. You'll HAVE to start with this one to make the project start!

---

### 3 - Keep the `order` package for last
This is the most complex package and it depends on both `product` and `user`.

It must be implemented last.

---

### 4 - Check the entities first
The entities are intentionally left as they are.

You are not here to design the architecture, but to implement business rules.

Pay attention to:
- fields
- column constraints
- IDs
- relationships

---

### 5 - Check the DTOs as well
DTOs are the objects returned to the frontend (or as API responses).

They must not expose all entity fields.

Example:
A password is sent by the user to the backend, but must **never** be returned in a response.

---

### 6 - Some methods are already implemented
Some methods in the `product` and `user` services are already fulfilled.

Their pedagogical value is limited, but they are required for the `order` package.
They also illustrate when and how such methods should be used.

---

## Utility classes

Some utility classes (like mappers or password utilities) have private constructors that throw exceptions.

You **do not need to modify or understand their internals** in depth.

They exist to:

* prevent instantiation
* reduce boilerplate
* improve readability

Focus on business logic, not infrastructure details.

---

### 7 - Do not forget unit tests
The solution has around **90% test coverage**.

Every class (except `Application.java`) has its own test class.

Some tests are already implemented to serve as examples:
- one controller test (the user controller's tests)
- one service test (the product's service tests)
- some mapper test (the one of the methods that are still presents, one in the user package, one in the product package)

Use them as references.

---

### 8 - You can use AI, but do not ask it for the solution
AI tools like ChatGPT are fully capable of generating entire classes if given enough information.

However, blindly copy/pasting solutions defeats the purpose of this exercise.

Use AI to:
- understand concepts
- clarify errors
- reason about edge cases

Not to solve everything for you.

---

### 9 - there are postman collections in resource you can use!
The resource package contains a postman folder with all the needed requests you might want to use for every action in every controller.

Import the request of manual_tests folder, they are already prepared for the use of the controller's definitions.

Keep the collection in "end_to_end_tests" for the end. It will do a full e2e tests for a dedicated endpoint.

This is the result you should get once you'll be at the end of the exercice: the end to end collection must passes.

---

### 10 - reminder : the default DB is a h2 db
So it resets everytime you reboot the project. Keep that in mind if you want to test an endpoint.

You can use one of the other configuration files, but you'll have to adapt them to your local installtion.

h2 should be ready to use in it's current state.

Good luck, and take your time. This project is designed to make you think.
