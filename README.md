# file-tree

## Task

Implement a web-based file manager that observes file changes. The work has been done in the framework of a practice-based research in computer science that took place in Exadel, 2013.

## Steps

1) [x] Implement a file indexing console application

    _Implement console application which takes directory path as argument._

    The application should:
    1) [x] Create an index for each file in the target directory. Index should contain file name, file size and last update fields.
    2) [x] Collection of indexes should be serialised as XML on disc.
    3) [x] Before serialisation application should check if previously serialised data for target directory already exists.
    4) [x] application should print complete report about files changes based on they previous and current index data.
    5) [x] After report old serialised data should be rewritten by actual.

2) [x] Provide choice to use DB file indexes storage

    _Make application more flexible: provide choice to store file indexes in DB._
    1) [x] Develop interface for required directory and file indexes operations. Refactor existed code to make it an implementation of the interface.
    2) [x] Develop another implementation of the interface which will use DB as file indexes storage. Write SQL script for DB creation. Store DB settings in application properties file. Use Spring framework JDBC data access abstraction for the implementation.
    3) [x] Use Inversion of Control (IoC) pattern to choose between two implementations. Create Spring context for the application. Select an implementation depending on key from application properties.

3) [ ] Create WEB application

    _Create WEB application based on existed functionality._
    1) [x] Use Servlets/JSP technology to develop WEB application. Use Tomcat as J2EE web container.
    2) [x] The application should allow file tree navigation providing list of subdirectories as well as file changes indexes.
    3) [ ] Provide user control for directory file indexes update.
    4) [x] _(Optional)_ Use Spring Web MVC for the implementation.

Technologies: Maven, J2EE, Servlets/JSP, Tomcat, MVC, Spring Framework.

## Configuration

### MySQL Database

1) Create database. Find and execute `src/main/resources/DB create.sql`.
2) Tweak JDBC connection in `jdbc connection.properties` file.
3) Set `app.use.implementation` to either `springDBService` or `dbService` in `application.properties` file.

### XML

1) Set `app.use.implementation` to `xmlService` in `application.properties` file.
