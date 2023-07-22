[![Run tests for posmulten hibernate](https://github.com/starnowski/posmulten-hibernate/actions/workflows/posmulten-hibernate.yml/badge.svg)](https://github.com/starnowski/posmulten-hibernate/actions/workflows/posmulten-hibernate.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.starnowski.posmulten.hibernate/core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.starnowski.posmulten.hibernate%22%20AND%20a:%22core%22)

# posmulten-hibernate

* [Introduction](#introduction)
    * [Difference between hibernate discriminator approach and posmulten](#difference-between-hibernate-discriminator-approach-and-posmulten)
* [Basic usage](#basic-usage)
    * [Maven](#maven)
    * [Schema generation](#schema-generation)
        * [Hibernates SessionFactory for schema creation for Hibernate 5](#hibernates-sessionfactory-for-schema-creation-for-hibernate-5)
        * [Hibernates configuration for schema generation for Hibernate 5](#hibernates-configuration-for-schema-generation-for-hibernate-5)
        * [Java model](#java-model)
    * [Client communication with database for Hibernate 5](#client-communication-with-database-for-hibernate-5)
        * [Hibernates configuration for application connection for Hibernate 5](#hibernates-configuration-for-application-connection-for-hibernate-5)
        * [Open connection for tenant for Hibernate 5](#open-connection-for-tenant-for-hibernate-5)
* [Tenant column as part of the primary key in schema design](#tenant-column-as-part-of-the-primary-key-in-schema-design)
    * [Java model with shared tenant column](#java-model-with-shared-tenant-column)
        * [Hibernate issue related to overlapping foreign keys](#hibernate-issue-related-to-overlapping-foreign-keys)
    * [Hibernates configuration for schema with shared tenant column](#hibernates-configuration-for-schema-with-shared-tenant-column)
* [Properties](#properties)
* [Reporting issues](#reporting-issues)
* [Project contribution](#project-contribution)

## Introduction

Project is integration of Posmulten and Hibernate libraries.
**Posmulten generates DDL statements only for the Postgres database. This means that the project is compatible only with this database engine since version 9.6.
The are two main goal for this project.**
The first is to generate DDL statements that create Multi-tenant architecture with a shared schema strategy based on the java model (currently available only for Hibernate 5).
For more information on how the Posmulten helps achieve this isolation strategy or what are other Multi-tenant architecture strategies, go to [project website](https://github.com/starnowski/posmulten). 
Generated DDL statement can be executed during integration tests or used by tools that apply changes to the database, like [Liquibase](https://www.liquibase.org/) or [Flyway](https://flywaydb.org/).
The second goal is to help communicate between the database and its client.

### Difference between hibernate discriminator approach and posmulten 

There is a big difference between the newly added hibernate feature [partitioned (discriminator) data](https://docs.jboss.org/hibernate/stable/orm/userguide/html_single/Hibernate_User_Guide.html#multitenacy-discriminator)  the posmulten library on how shared schema isolation is being achieved.
Hibernate as ORM framework adds to each statement sent to database condition in which it compares tenant column for the row with tenant values stored in the current session.
Posmulten is doing it differently. It generates DDL statements that create the Row Level Security Policy for tables that generally check if the tenant which is set for the connection is the same as the database row read or updated by SQL statement.
Both approaches have cons and pros.
Hibernate approach's benefit is that it can be used with other database engines.
Posmulten can only be used for the Postgres engine.
On the other side Hibernate creates potencial constraint in case when there is more than one project that use database.
In this situation other project also need to use Hibernate.
Posmulten gives flexibility in such situations because it generates security policies on the database level.
That means that other projects which use the same database do not have to use a posmulten project or even java.
A developer needs to ensure that the correct session property is being set with the tenant identifier during [connection establishment](https://github.com/starnowski/posmulten#connecting-to-database).

## Basic usage
### Maven
For Hibernate 5 add project to your pom.xml
```xml
        <dependency>
            <groupId>com.github.starnowski.posmulten.hibernate</groupId>
            <artifactId>hibernate5</artifactId>
            <version>0.2.0-SNAPSHOT</version>
        </dependency>

        <!-- hibernate dependency -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>5.6.3.Final</version>
        </dependency>
```

For Hibernate 6 add project to your pom.xml
```xml
        <dependency>
            <groupId>com.github.starnowski.posmulten.hibernate</groupId>
            <artifactId>hibernate6</artifactId>
            <version>0.2.0-SNAPSHOT</version>
        </dependency>

        <!-- hibernate dependency -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.2.5.Final</version>
        </dependency>
```

### Schema generation

With the help of the Hibernate ORM framework, the project creates DDL statements that generate Multi-tenant architecture with a shared schema strategy.
The generated DDL statements can be used during integration tests and by tools that apply changes to the database, like [Liquibase](https://www.liquibase.org/) or [Flyway](https://flywaydb.org/).

#### Hibernates SessionFactory for schema creation for Hibernate 5

For Hibernate 5 use below code:

To create Hibernate session, we need to add few service initiators from project.

```java

import com.github.starnowski.posmulten.hibernate.hibernate5.context.DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.DefaultSharedSchemaContextBuilderProviderInitiator;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.PosmultenUtilContextInitiator;
import com.github.starnowski.posmulten.hibernate.hibernate5.schema.SchemaCreatorStrategyContextInitiator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

//...

final StandardServiceRegistry registry=new StandardServiceRegistryBuilder()
        .addInitiator(new SchemaCreatorStrategyContextInitiator())
        .addInitiator(new DefaultSharedSchemaContextBuilderProviderInitiator())
        .addInitiator(new DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator())
        .addInitiator(new PosmultenUtilContextInitiator())
        .configure("hibernate.schema-creator.cfg.xml")
        .build();

        SessionFactory factory=new MetadataSources(registry)
        .buildMetadata().buildSessionFactory();
```

#### Hibernates configuration for schema generation for Hibernate 5
To hibernate configuration there need to be added few properties.

_hibernate.schema-creator.cfg.xml_

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<hibernate-configuration xmlns="http://www.hibernate.org/xsd/orm/cfg">
    <session-factory>
        <!-- ... -->
        <property name="hbm2ddl.auto">create-drop</property> <!-- create, create-drop -->
        <property name="schema_management_tool">
            com.github.starnowski.posmulten.hibernate.hibernate5.schema.PosmultenSchemaManagementTool
        </property>
        <property name="posmulten.grantee">posmhib4-user</property>
        <!-- ... -->
    </session-factory>
</hibernate-configuration>
```

The PosmultenSchemaManagementTool type needs to be set as a schema management tool by setting its package name with the property "schema_management_tool".
The configuration also requires setting the user to which Posmulten will generate constraints that provide the expected isolation level.
This should be the same user used by the application for normal [communication](#client-communication-with-database) with the database

**Grantee and schema creation user can be the same (database owner). There might be a little harder with setting data for tests.**

#### Java model

By default, all tables with Hibernate or JPA annotations are treated as non-multitenant.
That is why each table that is supposed to be multi-tenant should contain the annotation "TenantTable".

```java

import com.github.starnowski.posmulten.hibernate.hibernate5.TenantTable;

import javax.persistence.*;

@Table(name = "user_info")
@TenantTable
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;
    private String username;
    private String password;
    @OneToMany(mappedBy = "user")
    private Set<UserRole> roles;
    @OneToMany(mappedBy = "author")
    private Set<Post> posts;
}

```

The multi-tenant table can have a relation to the non-multitenant table.

#### Hibernates SessionFactory for schema creation for Hibernate 6

Important! Module for integration with Hibernate 6 does not have implemented generation of DDL statements based on Java model right now.
Instead of that it required to attach configuration file that 

For Hibernate 6 use below code:

To create Hibernate session, we need to add few service initiators from project.

```java

import com.github.starnowski.posmulten.hibernate.hibernate6.connection.SharedSchemaConnectionProviderInitiatorAdapter;
import com.github.starnowski.posmulten.hibernate.hibernate6.context.SharedSchemaContextProvider;
import com.github.starnowski.posmulten.hibernate.hibernate6.context.SharedSchemaContextProviderInitiator;
import com.github.starnowski.posmulten.hibernate.test.utils.MapBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import com.github.starnowski.posmulten.postgresql.core.context.decorator.DefaultDecoratorContext;
import com.github.starnowski.posmulten.postgresql.core.db.DatabaseOperationExecutor;
import com.github.starnowski.posmulten.postgresql.core.db.operations.exceptions.ValidationDatabaseOperationsException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

//...

final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        .addInitiator(new SharedSchemaContextProviderInitiator(this.getClass().getResource("/integration-tests-configuration.yaml").getPath(), DefaultDecoratorContext.builder()
        .withReplaceCharactersMap(MapBuilder.mapBuilder().put("{{template_schema_value}}", "public")
        .put("{{template_user_grantee}}", "posmhib4-user").build()).build()))
        .configure("hibernate.schema-creator.cfg.xml")
        .build();

        SessionFactory factory = new MetadataSources(registry)
        .buildMetadata().buildSessionFactory();
```

### Client communication with database for Hibernate 5

To create Hibernate session, we need to add few service initiators from project.

```java
import com.github.starnowski.posmulten.hibernate.hibernate5.connections.CurrentTenantPreparedStatementSetterInitiator;
import com.github.starnowski.posmulten.hibernate.hibernate5.connections.SharedSchemaConnectionProviderInitiatorAdapter;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.DefaultSharedSchemaContextBuilderProviderInitiator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

    SessionFactory getPrimarySessionFactory(){
final StandardServiceRegistry registry=new StandardServiceRegistryBuilder()
        .addInitiator(new SharedSchemaConnectionProviderInitiatorAdapter())
        .addInitiator(new DefaultSharedSchemaContextBuilderProviderInitiator())
        .addInitiator(new CurrentTenantPreparedStatementSetterInitiator())
        .configure() // configures settings from hibernate.cfg.xml
        .build();

        SessionFactory factory=new MetadataSources(registry)
        .buildMetadata().buildSessionFactory();
        return factory;
        }
```

#### Hibernates configuration for application connection for Hibernate 5
For correct client communication with database to hibernate configuration there need to be added few properties.

_hibernate.cfg.xml_

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<hibernate-configuration xmlns="http://www.hibernate.org/xsd/orm/cfg">
    <session-factory>
        <!-- ... -->
        <property name="hibernate.multiTenancy">SCHEMA</property>
        <property name="hibernate.multi_tenant_connection_provider">
            com.github.starnowski.posmulten.hibernate.hibernate5.connections.SharedSchemaMultiTenantConnectionProvider
        </property>
        <property name="hibernate.tenant_identifier_resolver">
            com.github.starnowski.posmulten.hibernate.hibernate5.CurrentTenantIdentifierResolverImpl
        </property>
        <property name="posmulten.schema.builder.provider">lightweight</property>
        <!-- ... -->
    </session-factory>
</hibernate-configuration>
```

For correct behavior, the posmulten integration uses the "SCHEMA" strategy which is why it is required to specify this value for the "hibernate.multiTenancy" property.
There are two other components that need to be specified:
    -   "com.github.starnowski.posmulten.hibernate.hibernate5.connections.SharedSchemaMultiTenantConnectionProvider" as "hibernate.multi_tenant_connection_provider"
    -   "com.github.starnowski.posmulten.hibernate.hibernate5.CurrentTenantIdentifierResolverImpl" as "hibernate.tenant_identifier_resolver"
And last but not least to have fewer things to set up we have to specify the property "posmulten.schema.builder.provider" with value ["lightweight"](#lightweight).
By default configuration context used for session factory initialization is ["full"](#full).

### Open connection for tenant for Hibernate 5
Below there is an example how connect and execute operation for tenant "Ten1".

```java

    private Session openPrimarySession() {
            return primarySessionFactory.openSession();
    }

    private User findUserByUsername(Session session, String username) {
        Query<User> query = session.createQuery("FROM User as user WHERE user.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    void test() {
        setCurrentTenant("Ten1");
        try (Session session = openPrimarySession()) {
             // WHEN
            User current =  findUserByUsername(session, "Simon");
            
            // THEN
            assertThat(current).isNotNull();
            assertThat(current.getUsername()).isEqualTo("Simon");
        }
            
        setCurrentTenant(tt);
        try (Session session = openPrimarySession()) {
            Transaction transaction = session.beginTransaction();

            // WHEN
            int numberOfDeleteRecords = session.createNativeQuery(String.format("UPDATE user_info SET password = 'YYY' WHERE username = '%s'", "Simon")).executeUpdate();
            session.flush();
            transaction.commit();
        }

    }
```

## Tenant column as part of the primary key in schema design

The [basic usage](#basic-usage) section described schema example assumes that the tenant discriminator column is not part of the primary key.
The main disadvantage of this approach might come to light when there will be project requirements for migrating tenant data between databases.
If there is no such requirement for the project then it is okay to have a primary key without a tenant column and not included it in the unique constraint for the primary key.
In case when we have such requirements besides the primary key we need to make sure that foreign key columns do not contain redundant columns in their reference to the tenant column from a different table.
Foreign keys have to share the tenant column with primary keys.
Generally, all unique constraints (except for dictionary tables that do not have to be multi-tenant) should be aware of the tenant column.

### Java model with shared tenant column

To better demonstrate the shared tenant column between keys, we will create two classes representing composite keys.

```java
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StringPrimaryKey implements Serializable {

    private String stringKey;

    private String tenant;

    // Getters, Setters, Equals and HashCode
}
```

```java
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LongPrimaryKey implements Serializable {

    private Long key;
    private String tenant;

    // Getters, Setters, Equals and HashCode
}
```

Below there is an example of two entities with shared tenant column

```java
import com.github.starnowski.posmulten.hibernate.hibernate5.TenantTable;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;

import javax.persistence.*;

@Table(name = "user_info_nonforeignkeyconstraint")
@TenantTable(tenantIdColumn = "tenant")
public class User {

    @EmbeddedId
    @AttributeOverride(name = "stringKey", column = @Column(name = "user_id"))
    @AttributeOverride(name = "tenant", column = @Column(name = "tenant", insertable = false, updatable = false))
    private StringPrimaryKey primaryKey;
    private String username;
    private String password;

    @OneToMany(mappedBy = "author", fetch = LAZY)
    @JoinColumnsOrFormulas(value = {
            //name --> Post column, referencedColumnName -- User column
            @JoinColumnOrFormula(column = @JoinColumn(name = "tenant_id", referencedColumnName = "tenant")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    })
    private Set<Post> posts;

    // Getters and Setters
}
```

```java
import com.github.starnowski.posmulten.hibernate.hibernate5.TenantTable;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;

@Table(name = "posts_nonforeignkeyconstraint")
@TenantTable
@IdClass(LongPrimaryKey.class)
public class Post {

    @Id
    @GeneratedValue
    private long key;
    @Id
    @Column(name = "tenant_id", insertable = false, updatable = false)
    private String tenant;

    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(formula = @JoinFormula(value = "tenant_id", referencedColumnName = "tenant")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "user_id", referencedColumnName = "user_id"))
    })
    private User author;

    @Column(columnDefinition = "text")
    private String text;

    // Getters and Setters
}
```

#### Hibernate issue related to overlapping foreign keys

Hibernate has known issue related to [overlapping foreign keys](https://hibernate.atlassian.net/browse/HHH-6221).
Usage of JoinColumnsOrFormulas annotation is a workaround for this issue but it has some drawbacks.
One of them is that the hibernate does not generates foreign key constraint for such declaration.
We need to add these statements manually in the import.sql file to solve this problem.

```sql
-- This is required because hibernate does not creates foreign key constraint for JoinColumnsOrFormulas annotation
ALTER TABLE posts_nonforeignkeyconstraint ADD CONSTRAINT fk_posts_users_author_manual_added FOREIGN KEY (user_id, tenant_id) REFERENCES user_info_nonforeignkeyconstraint;
```

**Important!** There is a plan to add  [features](https://github.com/starnowski/posmulten-hibernate/issues/17) to this project to solve this problem.

#### Hibernates configuration for schema with shared tenant column

Configuration looks almost the same as for basic [use case](#hibernates-configuration-for-schema-generation) with one additional property.
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<hibernate-configuration xmlns="http://www.hibernate.org/xsd/orm/cfg">
    <session-factory>
        <!-- ... -->
        <property name="posmulten.foreignkey.constraint.ignore">true</property>
        <!-- ... -->
    </session-factory>
</hibernate-configuration>
```

By default, the project adds a constraint that checks if a foreign key belongs to the current tenant.
In a situation when foreign and primary key shares the same tenant column which is monitored by RLS policy created by posmulten, such constraint is redundant.
The "posmulten.foreignkey.constraint.ignore" property allows to ignore of adding this constraint for foreign key.

## Properties
| Property name |   Type    |   Required  |   Description |
|---------------|-----------|---------------|---------------|
|hibernate.posmulten.grantee |    String  |   [full](#full) |   Database user to which Posmulten will generate constraints that provide the expected isolation level. This should be the same user used by the application for normal communication with the database   |
|hibernate.posmulten.schema.builder.provider |    String  |   No |   Configuration context used for session factory initialization. By default the ["full"](#full) is being used   |
|hibernate.posmulten.foreignkey.constraint.ignore |    Boolean  |   No |   For value "true", the library ignores adding this constraint that checks if a foreign key belongs to the current tenant  |
|hibernate.posmulten.tenant.id.property |    String  |   No |   Default name of column that stores tenant identifier. |
|hibernate.posmulten.tenant.id.set.current.as.default |    Boolean  |   No |   Generate a statement that sets a default value for the tenant column in all tables. Default value is "true" |
|hibernate.posmulten.tenant.id.values.blacklist |    String  |   No |   An array of invalid values for tenant identifier. The array needs to have at least one element. Ids are separated by comma |
|hibernate.posmulten.tenant.column.java.type |    String  |   No |   Java type that represents tenant identifier which is being used in SQL statement that sets a current tenant. Available values are "long", "string" and "custom". The default value is "string". For "custom" there needs to be also "hibernate.posmulten.tenant.column.java.type.custom.resolver" property defined |
|hibernate.posmulten.tenant.column.java.type.custom.resolver |    String  |   No |   Java type that implements com.github.starnowski.posmulten.hibernate.hibernate5.connections.ICurrentTenantPreparedStatementSetter interface which objective is to map correctly passed tenant value in prepared SQL statement |
|hibernate.posmulten.function.getcurrenttenant.name |    String  |   No |   Name of SQL function that returns current tenant value |
|hibernate.posmulten.function.setcurrenttenant.name |    String  |   No |   Name of SQL function that sets current tenant value |
|hibernate.posmulten.function.equalscurrenttenantidentifier.name |    String  |   No |   Name of SQL function that checks if the identifier passed as argument is equal to the current tenant value |
|hibernate.posmulten.function.tenanthasauthorities.name |    String  |   No |   Name of SQL function that checks if the current tenant for the database session has access to table row based on tenant column  |
|hibernate.posmulten.metadata.table.additional.enrichers |    String  |   No |   An array of subtypes of com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher interface that will be invoked after default enrichers. The array needs to have at least one element and each element should be a full class name with a package. Types are separated by comma  |
|hibernate.posmulten.metadata.additional.enrichers |    String  |   No |   An array of subtypes of com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderMetadataEnricher interface that will be invoked after default enrichers. The array needs to have at least one element and each element should be a full class name with a package. Types are separated by comma  |

##### lightweight 
Configuration context without any redundant thing that allows for the application to establish connections to the database

##### full 
Configuration context needed to set up session factory for schema creation. It also can be used by the application to establish connections to the database

# Reporting issues
* Any new issues please report in [GitHub site](https://github.com/starnowski/posmulten-hibernate/issues)

# Project contribution
* Look for open issues or create your own
* Fork repository on Github and start applying your changes to master branch or release branch
* Follow CONTRIBUTING.md document for coding rules
* Create pull request
