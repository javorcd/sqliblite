
[Twitter Bootstrap]:http://twitter.github.com/bootstrap/
[keymaster.js]:https://github.com/madrobby/keymaster
[jQuery]:http://jquery.com
[@tjholowaychuk]:http://twitter.com/tjholowaychuk
[express]:http://expressjs.com
[AngularJS]:http://angularjs.org
[Gulp]:http://gulpjs.co
# SqLibLite - sqlite library for android #

## Introduction ##

This library adds functionality to the base android api SQLite. Offers numerous classes and methods that help us to create SQL statements in a simple, readable and safely manner.

To understand the usability of this library we will compare it with android SQLite API by example. Suppose we want to build a SQL statement like the following:

```sh
SELECT customer_id, customer_name FROM customers WHERE customer_spending > 1000
```
To execute this sentence in the native android library should do something like the following:

```sh
String sql = android.database.sqlite.SQLiteQueryBuilder.buildQueryString(
false, "customers", new String[] {"customer_id", "customer_name"},
   "customer_spending > 10000", null, null, null, null);
```

Very difficult to understand at the first time , since many arguments are passed and we have to be careful not to confuse . Keep it in development increases the complexity
Let's see how we would use this library:

```sh
String sql = new Select().select("customer_id","customer_name")
                .from("customers")
                .where("customer_spending", Comparison.greater(1000)
```
As you see, it is much more readable and semantic. With this simple example we can see the power and ease of use of this library.

## Test app ##

A test application with which to see how the library works.

```sh
git clone https://mincore@bitbucket.org/mincore/agenda-sqliblite.git
```

To install in an android device can do the following:

```sh
./gradlew installDebug
```
The result will be something like the following:

```sh
Installing APK 'app-debug.apk' on 'android - 4.3 - API 18 - 4.3'
Installed on 1 device.
```

## Integration ##

**Prerequisites**

Target API >= 15.

Environment variables **ANDROID_HOME** and **JAVA_HOME**.

We can create *local.properties* in the project root indicating the path to the Android SDK:

```sh
sdk.dir=/opt/android/android-sdk
```

**Compilation**

To use this library in android projects follow a few simple steps:

Clone this repository:

```sh
mkdir sqliblite
cd sqliblite
git clone https://github.com/javorcd/sqliblite.git
```
Compile the library:

```sh
./gradlew clean build
```
Copy the generated .jar to a libs project folder

Add to the **build.gradle** the following line:

```sh
compile files('libs/sqliblite.jar')
```

## General structure ##

The library is divided generally in:

**DML (data manipulation language)**

Within this library are four basic classes to perform these operations:

* Select
* Insert
* Update
* Delete

**DDL (data definition language)**

It allows you to define data structures.

* CreateTable
* DropTable

This set of classes will be those that we use to create our SQL statements

## Generate queries ##

Depending on the type of operation you want to perform will use one or the other. To begin we start seeing how to use the Select class.

### Select ###

Used to query data from the database. We'll see examples of use, for this purpose we write a query in SQL and see which is the equivalent of using this library.

**Example 1**
```sh
SELECT * FROM customers
```
We would write:

```sh
String sql = new Select().select().from("customers");
```

**Example 2**

```sh
SELECT name, lastname FROM customers ORDER BY balance ASC
```
We would write::

```sh
String sql = new Select().select("name", "lastname")
                 .from("customers").orderBy("balance").asc();
```

**Example 3**

```sh
SELECT DISTINCT name FROM customers WHERE balance > 100 AND direction = 'calle falsa'
```
We would write:

```sh
String sql = new Select().selectDistinct("name").from("customers")
                .where("balance", Comparison.greater(100))
                .and("direction", Comparison.equal("direccion false"))
```

**Example 4**

```sh
SELECT * FROM customers CROSS JOIN reserves
```
We would write:

```sh
String sql = new Select().select().from("customers").crossJoin("reserves")
```

**Example 5**

```sh
SELECT name, lastname FROM customers INNER JOIN reserves USING(customer_id)
```
We would write:

```sh
String sql = new Select().select("name","lastname")
                 .from("customers").innerJoin("reserves")
                 .using("customer_id");
```

### Insert ###

This class generates queries to persist values ​​in the database. We can see cases of use in the following examples:

**Example 1**

```sh
INSERT INTO customers VALUES(1, "javier", "martinez", "calle falsa")
```
We would write:

```sh
String sql = new Insert().insert("customers")
                .values(1, "javier", "martinez", "calle falsa")
```

### Update ###

This class generates queries that are used to modify existing values ​​in the database. Let's see some examples:

**Example 1**

```sh
UPDATE customers SET name = 'javier', balance = 1234 WHERE customer_id = 1
```
We would write:

```sh
String sql = new Update().update("customers").set(
                  new Value("name", "javier"), new Value("balance", 1234)
                     .where("customer_id", Comparison.equal(1));
```

**Example 2**

```sh
UPDATE customers SET balance = 1244 WHERE name = 'javier'
```
We would write:

```sh
String sql = new Update().update("customers").set(
               new Value("balance", 1244)).where(
                  "name", Comparison.equal("javier")
```

### Delete ###

Generates queries to delete rows from the database. Some examples:

**Example 1**

```sh
DELETE FROM customers
```
We would write:

```sh
String sql = new Delete().delete("customers")
```

**Example 2**

```sh
DELETE FROM customers WHERE customer_id = 10
```
We would write:

```sh
String sql = new Delete().delete("customers").where("customer_id",
                   Comparison.equal(10))
```

**Example 3**

```sh
DELETE FROM customers WHERE name = 'javier' AND balance > 1000
```
We would write:

```sh
String sql = new Delete().delete("customers")
                .where("name",Comparison.equal("javier"))
                .and("balance", Comparison.greater(1000))
```
## Complex queries ##

The library supports the creation of complex queries using different joins and UNION that SQLite provides, but each subquery must be created separately from the principal, for example if we make the following query:

```sh
SELECT DISTINCT name FROM customers WHERE EXISTS (SELECT id FROM orders WHERE customer_id = 10)
```
we have to do something like this:

```sh
// child query
String exists = new Select().select("id").from("orders").where("customer_id", Comparison.equal(10))

// master query
String sql = new Select().selectDistinct("name").from("customers").where().exists(exists)
```
Now we can execute the query correctly.

## Create and delete tables ##

To create a new table will use the CreateTable class. For each new column have to specify a set of values, such as the type of value (represented by the **Type** class) and one optional parameter indicating the restriction of the field, represented by the class **Constraint** .

Example of table creation:

```sh
String sql = CreateTable.name("customers").colums(
       Colum.value("id", Type.INTEGER, Constraint.PRIMARY_KEY_AUTOINCREMENT),
       Colum.value("name", Type.VARCHAR, Constraint.NOT_NULL),
       Colum.value("direction", Type.VARCHAR, Constraint.NULL),
       Colum.value("tlf", Type.VARCHAR)));
```
To delete a table the DropTable class is used. It's use is very simple:

```sh
String sql = DropTable.drop("customers")
```

## Create the database ##

To create a database you must create a subclass of **AbstractDatabase** and implement the abstract methods .

```sh
public BDExample extends AbstractDatabase {

    private static final String NAME_BD = "ejemplo_db";
    private static final int VERSION_BD = 1;

    public BDExample(Context cxt) {
        super(ctx);
    }

    @Override
    public String getName() {
        return NOMBRE_BD;
    }

    @Override
    public int getVersion() {
        return VERSION_BD;
    }

    @Override
    protected void executeCreate(SQLiteDatabase database) {
        // create
    }

    @Override
    protected void executeUpgrade(SQLiteDatabase database, int oldVer, int newVer) {
       // update
    }
}
```
Now we have to create the class that will perform operations on the database. For this first a new instance of the database is created:

```sh
final BDExample bd = new BDExample(getapplicationContext());
```

```sh
final DataOperation operations = new DataOperation(bd);
```
With this we can now perform operations on our database.

### Insert values ###


```sh
String sql = new Insert().insert("customers")
                .values(1, "javier", "casanova", "calle falsa").buildStatement();

// execute the query

long id = operations.insert(sql);
```
Returns the id that has been inserted into the database.

### Delete values ###

```sh
String sql = new Delete().delete("customers").where("name", Comparison.equal("javier"))
                  .buildStatement();

// execute the query

int deleted = operations.delete(sql);
```
Returns the number of rows deleted in the database.

### Update Values ###

```sh
String sql = new Update().update("customers").set(new Value("balance", Comparison.equal(1000)))
                .where("city", Comparison.equal("ferrol")).buildStatement();

// execute the query

int updated = operations.update(sql);
```
Returns the number of updated rows.

### Select data ###

Consult data in the database is somewhat different from other operations, and we need to build a new entity from the returned data. For example, if we consult the clients we have in our database we have to "tell" how we want the library that maps the returned items.

To do this the libray provides a generic interface called **EntityMapper** that is responsible for receiving an object of type Cursor and make our data type. For example, if you have an entity type:

```sh
public Client {
   
   private String id;
   private String name;

   // Se omiten getters y setter por brevedad
}
```
To create a Client type mapper elements do something like the following:

```sh
class ClientMapper implements EntityMapper<Client> {

    @Override
    public Client mapEntity(Cursor cursor) {
        Client client = new Client();
        cliente.setId(cursor.getInt(cursor.getColumnIndex("id")));
        cliente.setName(cursor.getInt(cursor.getColumnIndex("name")));
        return client;
    }
}
```
Once this is understood, we will see how to retrieve all customers in the database:

```sh
String sql = new Select().select().from("customers").buildStatement();

// Execute the query

List<Client> clients = operations.get(sql, new ClientMapper());
```
Returns a list of clients that are in the database.

## Unit test and code coverage ##

Inside the library there is a folder for unit testing with several examples of creating queries. The framework used is JUnit version 4.12. For code coverage has been used jacoco and created a task to run with Gradle.

To run the unit test project run the following in the root directory of the project:

```sh
./gradlew clean sqliblite:test
```
When the process is complete, we can see the result of the test in an HTML page found in the directory:

```sh
build/reports/tests/unitTestDebug/index.html
```
To see code coverage should be run the next task in the project root directory:

```sh
./gradlew clean jacocoTestReport
```
This task will create a report in HTML at the following path:

```sh
build/reports/jacoco/jacocoTestReport/html/index.html
```