# posmulten-hibernate

* [Introduction](#introduction)
* [Basic usage](#basic-usage)
    * [Maven](#maven)
    * [Schema generation](#schema-generation)
* [Properties](#properties)

## Introduction

Project is integration of Posmulten and Hibernate libraries.
The are two main goal for this project.
The first is to generate DDL statements that create Multi-tenant architecture with a shared schema strategy based on the java model.
For more information on how the Posmulten helps to achieve this isolation strategy go to [project website](https://github.com/starnowski/posmulten).  
Generated DDL statement can be executed during integration tests or used by tools that apply changes to the database, like [Liquibase](https://www.liquibase.org/) or [Flyway](https://flywaydb.org/).
The second goal is to help communicate between the database and its client.

## Basic usage
### Maven
Add project to your pom.xml
```xml
        <dependency>
            <groupId>com.github.starnowski.posmulten.hibernate</groupId>
            <artifactId>core</artifactId>
            <version>0.1.0</version>
        </dependency>
```

### Schema generation

With the help of the Hibernate ORM framework, the project creates DDL statements that generate Multi-tenant architecture with a shared schema strategy.
The generated DDL statements can be used during integration tests and by tools that apply changes to the database, like [Liquibase](https://www.liquibase.org/) or [Flyway](https://flywaydb.org/).

#### Hibernates SessionFactory for schema creation


## Properties
| Property name |   Type    |   Required    |   Nullable    |   Description |
|---------------|-----------|---------------|---------------|---------------|
|[default_schema](#default_schema) |    String  |   Yes |   Yes |   Name of the database schema for which changes should be applied. |
