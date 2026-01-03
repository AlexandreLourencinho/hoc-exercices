# this is the mapper package.
It should do the mapping between dtos and entities.

I've left the update done here for you as an example. the other methods are cleaned, all should be used at some point.

---

## methods

### entityToDTOget
should return a ProductDTOGet from a ProductEntity

### dtoCreateToEntity
should return a new Entity from the ProductDTOCreate.

Do not forget: ids with auto-incremental bdd are filled by jpa when you save it in database. for it to happen, you have to let the id field null when creating a new entity.

### entityToDTOStock
should return a ProductDTOStock coming from an existing entity.

### dtoUpdateToEntity
already done here. it's to show you how the update is done.

You might think the setters are overkill, since some of the values may not have changed.

This is normal and intentional. updating a value by the same value won't affect the behavior of the jpa entity as long as you don't change the id.

