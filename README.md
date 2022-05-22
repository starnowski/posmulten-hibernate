# posmulten-hibernate

* [Introduction](#introduction)
* [Schema generation](#schema-generation)
* [Properties](#properties)

## Introduction

Project is integration of Posmulten and Hibernate libraries.
The are two main goal for this project.
First one is to generate DDL statements that creates Multi-tenant with shared schema strategy based on java model.
For more information on how the Posmulten helps to achieve this isolation strategy go to [project website](https://github.com/starnowski/posmulten).  
Generated DDL statement can be executed during integration tests or used by tools that apply changes to the database, like [Liquibase](https://www.liquibase.org/) or [Flyway](https://flywaydb.org/).
The second goal is to help communicate between the database and its client.

## Schema generation

## Properties
| Property name |   Type    |   Required    |   Nullable    |   Description |
|---------------|-----------|---------------|---------------|---------------|
|[default_schema](#default_schema) |    String  |   Yes |   Yes |   Name of the database schema for which changes should be applied. |
