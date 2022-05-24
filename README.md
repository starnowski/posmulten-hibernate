# posmulten-hibernate

* [Introduction](#introduction)
* [Basic usage](#basic-usage)
    * [Maven](#maven)
    * [Schema generation](#schema-generation)
        * [Hibernates SessionFactory for schema creation](#hibernates-sessionfactory-for-schema-creation)
        * [Hibernates configuration for schema generation](#hibernates-configuration-for-schema-generation)
* [Properties](#properties)

## Introduction

Project is integration of Posmulten and Hibernate libraries.
The are two main goal for this project.
The first is to generate DDL statements that create Multi-tenant architecture with a shared schema strategy based on the java model.
For more information on how the Posmulten helps achieve this isolation strategy or what are other Multi-tenant architecture strategies, go to [project website](https://github.com/starnowski/posmulten). 
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

To create Hibernate session, we need to add few service initiators from project.

```java

import com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator;
import com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderProviderInitiator;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContextInitiator;
import com.github.starnowski.posmulten.hibernate.core.schema.SchemaCreatorStrategyContextInitiator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addInitiator(new SchemaCreatorStrategyContextInitiator())
                .addInitiator(new DefaultSharedSchemaContextBuilderProviderInitiator())
                .addInitiator(new DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator())
                .addInitiator(new PosmultenUtilContextInitiator())
                .configure("hibernate.schema-creator.cfg.xml")
                .build();

        SessionFactory factory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
```

#### Hibernates configuration for schema generation
To hibernate configuration there need to be added few properties.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<hibernate-configuration xmlns="http://www.hibernate.org/xsd/orm/cfg">
    <session-factory>
        <!-- ... -->
        <property name="hbm2ddl.auto">create-drop</property> <!-- create, create-drop -->
        <property name="schema_management_tool">com.github.starnowski.posmulten.hibernate.core.schema.PosmultenSchemaManagementTool</property>
        <property name="posmulten.grantee">posmhib4-user</property>
        <!-- ... -->
    </session-factory>
</hibernate-configuration>
```

The PosmultenSchemaManagementTool type needs to be set as a schema management tool by setting its package name with the property "schema_management_tool".
The configuration also requires setting the user to which Posmulten will generate constraints that provide the expected isolation level.  


TODO

## Properties
| Property name |   Type    |   Required    |   Nullable    |   Description |
|---------------|-----------|---------------|---------------|---------------|
|[default_schema](#default_schema) |    String  |   Yes |   Yes |   Name of the database schema for which changes should be applied. |
