# File Tree
[![Codeship Status for naXa777/file-tree](https://app.codeship.com/projects/17e4d940-9903-0136-b15a-3601fc6081f4/status?branch=master)](https://app.codeship.com/projects/305398)

## Description

### Task

Implement a web-based file manager that observes file changes. The work has been done in the framework of a practice-based research in computer science that took place in Exadel, 2013.

### Steps

- [x] Implement a file indexing console application  
  _Implement console application which takes directory path as argument._  
  The application should:  
  - [x] Create an index for each file in the target directory. Index should contain file name, file size and last update fields.
  - [x] Collection of indexes should be serialised as XML on disc.
  - [x] Before serialisation application should check if previously serialised data for target directory already exists.
  - [x] Application should print complete report about file changes based on their previous and current index data.
  - [x] After report old serialised data should be rewritten by actual.

- [x] Provide choice to use DB file indexes storage  
  _Make application more flexible: provide choice to store file indexes in DB._  
  - [x] Develop interface for required directory and file indexes operations. Refactor existed code to make it an implementation of the interface.
  - [x] Develop another implementation of the interface which will use DB as file indexes storage. Write SQL script for DB creation. Store DB settings in application properties file. Use Spring framework JDBC data access abstraction for the implementation.
  - [x] Use Inversion of Control (IoC) pattern to choose between two implementations. Create Spring context for the application. Select an implementation depending on key from application properties.

- [ ] Create WEB application  
  _Create WEB application based on existed functionality._  
  - [x] Use Servlets/JSP technology to develop WEB application. Use Tomcat as J2EE web container.
  - [x] The application should allow file tree navigation providing list of subdirectories as well as file changes indexes.
  - [ ] Provide user control for directory file indexes update.
  - [x] _(Optional)_ Use Spring Web MVC for the implementation.

**Technologies**: Maven, J2EE, Servlets/JSP, Tomcat, MVC, Spring Framework.

## Configuration

You can choose to store index either in DB or XML.

### MySQL Database

1. Create database. Find and execute `src/main/resources/DB create.sql`.  
2. Tweak JDBC connection in `jdbc connection.properties` file.  
3. Set `app.use.implementation` to either `springDBService` or `dbService` in `application.properties` file if you want to use database as a storage.

### XML

Set `app.use.implementation` to `xmlService` in `application.properties` file if you want to use XML-based storage.

## Quick Start

### Build web app

    mvn clean package

Output: /target/file-tree.war

### Build console app

    mvn clean jar:jar
    
Output: /target/file-tree.jar

### Run web app

1. Deploy the application to Tomcat;
2. Open [http://localhost:8080/&lt;deploy-path&gt;](http://localhost:8080/file-tree) in a browser;
3. Guess credentials!
4. And browse your `D:\` drive from the browser!

### Run console app

    java -jar ./file-tree.jar "D:/example"
    
The first argument is a root folder for indexing.

## Screenshots

State 1    
![File Tree](/screenshots/filetree_1.png)

_Make changes... Refresh page_

State 2  
![File Tree](/screenshots/filetree_2.png)
