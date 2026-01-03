# The entity package.
There is no specific rules on this entity outside the id column, which is an integer auto - incremental.

More business rules will be found in user package, and even more in order package.

Do not forget: ids with auto-incremental bdd are filled by jpa when you save it in database. for it to happen, you have to let the id field null when creating a new entity.