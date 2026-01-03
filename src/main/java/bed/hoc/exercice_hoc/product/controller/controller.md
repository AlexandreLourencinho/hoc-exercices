# Here is the product controller.
In this package, this will be where most of your work will be, with the mapper.

## pay attention to the return type and the expected params
Following the endpoint, it can be request params, path params, a body, or even nothing.

You have to search how to make this work with the already built service to make the requests present in the postman collection
related to products in `resources/postman/manual_tests` to work.

---

## you have an example of controller in user
In the user package, the controller is done. you can take it as an example on how to make it.

Still, try to differentiate the different methods and params by yourself as much as you can. 

---

## again, chatgpt is fully capable of doing this alone
But we want you to be able to do it by yourself. take your time to understand what to call from the service, how to return it, etc.

---

## the return type of the different methods shouldn't be changed.
The return type is the expected one: just `ResponseEntity` without the `<>`. This is intentional.

---

## the path should'nt be changed as well
Unless you want to add a path parameter. you'll have to check the documentation to know how to do that correctly.

Not all methods expect a path parameter. the comments in the controller are here to tell you what is expected to be each endpoint.

i'll let you find which mapping annotation you'll need to make it work. Again, user controller is an example.

---

## don't forget to manage your try and catches.
For that, you'll have to analyse the product service, and check what method can throw which exception.

Each thrown exception should be catched where it can be thrown - no need to catch what's not thrown.

Also, you don't have here to catch the generic RuntimeException or Exception. 

it would hide any potential mistake made in the treatment : we don't want that.
You'll have to catch only the exceptions present in the exception package.

---

## methods descriptions:

### getProduct
get one product, returns a ProductDTOGet

### getProductStock
get the stock of one specified product. returns a ProductDTOStock.

Pay attention to the path on this one. if you change any path,you'll have to change the postman requests accordingly. PathParam parameters doesn't count as changing the path.

### getProducts
should return a list of products. it can be done with or without a list of ids to search specific products or all of them.

It's up to you to know how to do the check to call the right method in the service.

### createProduct
creates a product. expects a ProductDTOCreate. should return a ProductDTOGet.

This is a create endpoint, so the http method should differ than the previous ones.

Do not forget the @Valid for the body you'll need. again, check user controller or the documentation for more details

### updateProduct
Update an existing product. Expects a ProductDTOUpdate. Should return a ProductDTOGet.

this is an update endpoint. the http method should differ from the previous one.

### deleteProduct
delete one product.

This is a delete endpoint, so the HTTP method should differ from the previous ones.

### deleteProducts
delete a list of products from db. This one expects a request param of ids.
