
[Twitter Bootstrap]:http://twitter.github.com/bootstrap/
[keymaster.js]:https://github.com/madrobby/keymaster
[jQuery]:http://jquery.com
[@tjholowaychuk]:http://twitter.com/tjholowaychuk
[express]:http://expressjs.com
[AngularJS]:http://angularjs.org
[Gulp]:http://gulpjs.co
# SqLibLite - librería sqlite para android #

## Introducción ##

Esta librería añade funcionalidades prácticas a la api base de android SQLite. Ofrece numerosas clases y métodos que nos ayudan a crear sentencias SQL de una forma simple, legible y segura.

Para comprender la facilidad de uso de esta librería vamos a compararla con la api de android SQLite mediante un ejemplo. Supongamos que queremos construir una sentencia SQL como la siguiente:

```sh
SELECT cliente_id, cliente_nombre FROM clientes WHERE cliente_gasto > 1000
```
Para ejecutar esta sentencia en la librería nativa de android tendríamos que hacer algo como lo siguiente:

```sh
String sql = android.database.sqlite.SQLiteQueryBuilder.buildQueryString(
false, "clientes", new String[] {"cliente_id", "cliente_nombre"}, 
   "cliente_gasto > 10000", null, null, null, null);
```

Bastante complicado de comprender de un primer vistazo ya que se pasan muchos argumentos y necesitamos estar atentos para no confundirnos, a lo que se suma también la complejidad de mantenerlo en un desarrollo.

Vamos a ver como construiríamos esta misma sentencia usando esta librería:

```sh
String sql = new Select().select("cliente_id","cliente_nombre")
                .from("clientes")
                .where("cliente_gasto", Comparison.greater(1000)
```
Como ves, es mucho mas legible y semántico. Con este sencillo ejemplo podemos ver la potencia y facilidad de uso de esta librería.

## Aplicación de prueba ##

Se ha realizado una aplicación de prueba con la que poder ver como funciona la librería. 

Se puede obtener clonando el repositorio:

```sh
git clone https://mincore@bitbucket.org/mincore/agenda-sqliblite.git
```

Para instalarla en un dispositivo android se puede hacer lo siguiente:

```sh
./gradlew installDebug
```
Si tenemos un dispositivo conectado al ordenador al terminar veremos un mensaje como el siguiente informando que el proceso se ha realizado satisfactoriamente:

```sh
Installing APK 'app-debug.apk' on 'android - 4.3 - API 18 - 4.3'
Installed on 1 device.
```

## Como integrarla ##

**Requisitos antes de integrar**

El target api debe ser >= 15.

Tener las variables de entorno **ANDROID_HOME** y **JAVA_HOME** correctamente puestas en el sistema.

En caso de no tener la variable de entorno **ANDROID_HOME** podemos crear un archivo *local.properties* en la raíz del proyecto indicando la ruta al sdk de android:

```sh
sdk.dir=/opt/android/android-sdk
```

**Compilar**

Para usar esta librería en proyectos android seguiremos unos pasos muy sencillos:

Lo primero será clonar este repositorio, para ello podremos hacer algo como lo siguiente:

```sh
mkdir sqliblite
cd sqliblite
git clone https://mincore@bitbucket.org/mincore/sqliblite.git
```
Una vez realizado este paso tendremos que compilar el proyecto para generar un .jar. Lo haremos usando gradle. Estando en la raíz del proyecto haremos uso del comando:

```sh
./gradlew clean build
```
El siguiente paso es copiar el .jar generado en la carpeta outputs y pegarlo en la carpeta libs de nuestro nuevo proyecto de android.

Para poder usarla tendremos que añadir al archivo **build.gradle** la siguiente linea:

```sh
compile files('libs/sqliblite.jar')
```
Una vez realizado este paso ya estamos preparados para poder empezar a usar la librería en nuestros proyectos.

## Estructura general ##

La librería se divide de forma general en:

**DML (data manipulation language)**

Permite llevar a cabo operaciones de inserción, consulta, borrado y modificación de los datos que contiene una base de datos relacional usando el lenguaje SQL.

Dentro de esta librería encontramos cuatro clases fundamentales para poder realizar estas operaciones:

* Select
* Insert
* Update
* Delete

**DDL (data definition language)**

Permite definir estructuras de datos. Actualmente en la librería solo hay implementadas dos clases para realizar este tipo de operaciones. Estas son:

* CreateTable
* DropTable

Este conjunto de clases serán las que usaremos para crear nuestras sentencias SQL.

También se incluyen clases y métodos para generar consultas más complejas usando funciones de agregación y operadores lógicos. Se verán en detalle mas adelante.

## Como usarla para generar consultas ##

Dependiendo del tipo de operación que queramos realizar usaremos una u otra. Para comenzar empezaremos viendo como usar la clase Select.

### Clase Select ###

Usada para consultar datos de la base de datos. Vamos a ver ejemplos de uso, para ello escribiremos una consulta en SQL y veremos cual será el equivalente usando esta librería.

**Ejemplo 1**
```sh
SELECT * FROM clientes
```
Escribiríamos:

```sh
String sql = new Select().select().from("clientes");
```

**Ejemplo 2**

```sh
SELECT nombre, apellido FROM clientes ORDER BY saldo ASC
```
Escribiríamos:

```sh
String sql = new Select().select("nombre", "apellido")
                 .from("clientes").orderBy("saldo").asc();
```

**Ejemplo 3**

```sh
SELECT DISTINCT nombre FROM clientes WHERE saldo > 100 AND direccion = 'calle falsa'
```
Escribiríamos:

```sh
String sql = new Select().selectDistinct("nombre").from("clientes")
                .where("saldo", Comparison.greater(100))
                .and("direccion", Comparison.equal("direccion false"))
```

**Ejemplo 4**

```sh
SELECT * FROM clientes CROSS JOIN reservas  
```
Escribiríamos:

```sh
String sql = new Select().select().from("clientes").crossJoin("reservas")
```

**Ejemplo 5**

```sh
SELECT nombre, apellido FROM clientes INNER JOIN reservas USING(cliente_id)
```
Escribiríamos:

```sh
String sql = new Select().select("nombre","apellido")
                 .from("clientes").innerJoin("reservas")
                 .using("cliente_id");
```

### Clase Insert ###

Esta clase genera consultas para persistir valores en la base de datos. Podemos ver casos de uso en los siguientes ejemplos:

**Ejemplo 1**

```sh
INSERT INTO clientes VALUES(1, "javier", "casanova", "calle falsa")
```
Escribiríamos:

```sh
String sql = new Insert().insert("clientes")
                .values(1, "javier", "casanova", "calle falsa")
```

### Clase Update ###

Esta clase genera consultas que sirven para modificar valores existentes en la base de datos. Vamos a ver unos ejemplos:

**Ejemplo 1**

```sh
UPDATE clientes SET nombre = 'javier', saldo = 1234 WHERE cliente_id = 1
```
Escribiríamos:

```sh
String sql = new Update().update("clientes").set(
                  new Value("nombre", "javier"), new Value("saldo", 1234)
                     .where("cliente_id", Comparison.equal(1));
```

**Ejemplo 2**
```sh
UPDATE clientes SET saldo = 1244 WHERE nombre = 'javier'
```
Escribiríamos:

```sh
String sql = new Update().update("clientes").set(
               new Value("saldo", 1244)).where(
                  "nombre",Comparison.equal("javier")
```

### Clase Delete ###

Genera consultas para borrar registros de la base de datos. Algunos ejemplos:

**Ejemplo 1**

```sh
DELETE FROM clientes
```
Escribiríamos:

```sh
String sql = new Delete().delete("clientes")
```

**Ejemplo 2**
```sh
DELETE FROM clientes WHERE cliente_id = 10
```
Escribiríamos:

```sh
String sql = new Delete().delete("clientes").where("cliente_id", 
                   Comparison.equal(10))
```

**Ejemplo 3**

```sh
DELETE FROM clientes WHERE nombre = 'javier' AND saldo > 1000
```
Escribiríamos:

```sh
String sql = new Delete().delete("clientes")
                .where("nombre",Comparison.equal("nombre"))
                .and("saldo", Comparison.greater(1000))
```
## Consultas complejas ##

La librería soporta la creación de consultas complejas usando los diferentes JOINs y UNION que sqlite proporciona, pero cada subconsulta ha de ser creada por separado de la principal, por ejemplo si queremos realizar la siguiente consulta:

```sh
SELECT DISTINCT nombre FROM clientes WHERE EXISTS (SELECT id FROM pedidos WHERE cliente_id = 10)
```
tendremos que hacer algo equivalente en la librería:
```sh
// child query
String exists = new Select().select("id").from("pedidos").where("cliente_id", Comparison.equal(10))

// master query
String sql = new Select().selectDistinct("nombre").from("clientes").where().exists(exists)
```
Ahora podemos ejecutar la consulta de forma correcta.

## Crear tablas y borrarlas ##

Para crear una nueva tabla usaremos la clase CreateTable. Para cada nueva columna tendremos que especificar una serie de valores, como son el tipo de valor (representado por la clase **Type**) y otro parámetro opcional que indica la restricción del campo, representado por la clase **Constraint**.

Ejemplo de creación de una tabla:
```sh
String sql = CreateTable.name("clientes").colums(
       Colum.value("id", Type.INTEGER, Constraint.PRIMARY_KEY_AUTOINCREMENT),
       Colum.value("nombre", Type.VARCHAR, Constraint.NOT_NULL),
       Colum.value("direccion", Type.VARCHAR, Constraint.NULL),
       Colum.value("telefono", Type.VARCHAR)));
```

Para borrar una tabla se usa la clase DropTable. Su uso es muy sencillo:

```sh
String sql = DropTable.drop("clientes")
```

## Crear la base de datos ##

Para crear una base de datos hay que crear una subclase de **AbstractDatabase** e implementar los métodos abstractos.

```sh
public BDEjemplo extends AbstractDatabase {

    private static final String NOMBRE_BD = "ejemplo_db";
    private static final int VERSION_BD = 1;

    public BDEjemplo(Context cxt) {
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
        // Crear tablas
    }

    @Override
    protected void executeUpgrade(SQLiteDatabase database, int oldVer, int newVer) {
       // actualizar
    }
}
```
Ahora tendremos que crear la clase que realizará las operaciones sobre la base de datos que hemos creado. Para ello primero se crea una nueva instancia de la base de datos:

```sh
final BDEjemplo bd = new BDEjemplo(getapplicationContext());
```
Creamos una instancia de **DataOperation** y le pasamos la base de datos sobre la que se van a realizar las operaciones:

```sh
final DataOperation operations = new DataOperation(bd);
```
Con esto ya podremos realizar operaciones sobre nuestra base de datos.

### Insertar valores ###

Creamos una consulta y la ejecutamos:

```sh
String sql = new Insert().insert("clientes")
                .values(1, "javier", "casanova", "calle falsa").buildStatement();

// ejecutamos la query

long id = operations.insert(sql);
```
Devuelve el id que se ha insertado en la base de datos.

### Eliminar registros ###

Creamos una consulta que va a eliminar todos los clientes cuyo nombre sea javier.
```sh
String sql = new Delete().delete("clientes").where("nombre", Comparison.equal("javier"))
                  .buildStatement();

// ejecutamos la consulta

int eliminados = operations.delete(sql);
```
Devuelve el numero de filas eliminadas en la base de datos.

### Modificar valores ###

Creamos una consulta que modifica el saldo de los clientes que vivan en ferrol:

```sh
String sql = new Update().update("clientes").set(new Value("saldo", Comparison.equal(1000)))
                .where("ciudad", Comparison.equal("ferrol")).buildStatement();

// se ejecuta la consulta

int actualizados = operations.update(sql);
```
Devuelve el numero de filas actualizadas.

### Seleccionar datos ###

Consultar datos en la base de datos es algo diferente al resto de operaciones, ya que necesitamos construir una nueva entidad a partir de los datos devueltos. Por ejemplo, si queremos consultar los clientes que tenemos en nuestra base de datos tenemos que "decirle" a la librería cómo queremos que mapee los elementos devueltos.

Para ello la librería ofrece una interfaz genérica llamada **EntityMapper** que es la encargada de recibir un objeto de tipo Cursor y convertirlo a nuestro tipo de datos. Por ejemplo, tenemos una entidad del tipo:

```sh
public Cliente {
   
   private String id;
   private String nombre;

   // Se omiten getters y setter por brevedad
}
```
Para crear un mapeador de elementos tipo Cliente haremos algo como lo siguiente:

```sh
class MapeadorCliente implements EntityMapper<Cliente> {

    @Override
    public Persona mapEntity(Cursor cursor) {
        Cliente cliente = new Cliente();
        cliente.setId(cursor.getInt(cursor.getColumnIndex("id")));
        cliente.setNombre(cursor.getInt(cursor.getColumnIndex("nombre")));
        return cliente;
    }
}
```
Una vez entendido esto, vamos a ver como realizaríamos una consulta para ver todos los clientes de la base de datos:

```sh
String sql = new Select().select().from("clientes").buildStatement();

// Ejecutamos la consulta

List<Cliente> clientes = operations.get(sql, new MapeadorCliente());
```
Devuelve una lista con los clientes que hay en la base de datos.

## Test unitarios y cobertura de código ##

Dentro de la librería existe una carpeta para los test unitarios con varios ejemplos de creación de consultas. El framework usado es junit version 4.12. Para la cobertura de código se ha usado jacoco y se ha creado una tarea para ejecutar con gradle.

Para ejecutar los test unitarios del proyecto ejecuta lo siguiente en el directorio raíz del proyecto:

```sh
./gradlew clean sqliblite:test
```
Cuando el proceso termine, podemos consultar el resultado de los test en una página html que se encuentra en el directorio:

```sh
build/reports/tests/unitTestDebug/index.html
```
Para ver la cobertura de código habrá que ejecutar la siguiente tarea en el directorio raíz del proyecto:

```sh
./gradlew clean jacocoTestReport
```
Esta tarea creará un informe en html en la siguiente ruta:

```sh
build/reports/jacoco/jacocoTestReport/html/index.html
```