# Configuration file overview
Inside the **application.properties**, you have:
- the server port (you can change it as you want)
- You also have the spring profile active. 
 
By default, it will use the h2 profile, which will use the in memory h2 database.

## you will see three application-x.properties in addition of the main one.
The first one is th main one, just **application.properties**. It will always be taken into account.

- The **application-h2.properties** contains h2 information. database url, the console url (localhost:8080/h2-console), user and password to connect, the jpa config, etc.

- **application-mysql.properties**
this one is there if you want to configure a mysql database and work with it instead of h2 database

- **application-mariadb.properties**
  this one is there if you want to configure a mariadb database and work with it instead of h2 database, for example with heidiSql, like we did in the AFPA during our training.


# ⚠️ IMPORTANT NOTE about mysql and mariadb

If you decide to use MySQL or MariaDB, you must first install and configure the database on your machine.

You may need to update:
- the database URL
- the port
- the username
- the password

The provided configuration is a **default example** and may not work out of the box, depending on how your DBMS is installed and configured (for example: MariaDB with HeidiSQL, like we did at AFPA).

So keep in mind that adjusting the connection settings is totally normal.
