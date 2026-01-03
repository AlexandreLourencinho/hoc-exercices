# this is the product package, the first one you should consider doing, since it'll be the simpliest 

In this package, you won't have to make the service.

don't forget the rules : 

## pay attention to the entity and the dto
That's the best way of knowing what is expected to be registered in database, what is expected to be sent to the front / to the request,
and what is expected to be received from the front / the request.

---

## do not forget the unit tests !
the package will have a controller and a mapper to fill, but the unit tests have to be done too.

The unit tests are "prepared" : the tests class are already created and the methods too.

You'll have to fill them with the needed tests to make them work as their methods name suggests.

---

### The project won't compile unless you repair the repository !

since it's normally the package you'll start with, i've "broke" the repository for you to check how it is done to transform an interface to a JpaRepository

This is pretty simple and can be checked in the user package, where i'll let it as it is. But still, you'll have to do it for the project to compile.

### The unit tests for the service will fail
Until you repair the repository AND the mapper. Don't be surprised if it doesn't work at first, that's intended!
