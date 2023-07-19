package com.github.starnowski.posmulten.hibernate.hibernate6.integration.nonforeignkeyconstraint;

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
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.sql.Connection;
import java.sql.SQLException;

import static com.github.starnowski.posmulten.postgresql.core.db.DatabaseOperationType.*;

public class AbstractBaseNonForeignKeyConstraintItTest {

    private static SessionFactory primarySessionFactory;
    private static SessionFactory schemaCreatorSessionFactory;
    private final DatabaseOperationExecutor databaseOperationExecutor = new DatabaseOperationExecutor();
    protected Session schemaCreatorSession;
    protected Session primarySession;
    private ISharedSchemaContext sharedSchemaContext;

    protected SessionFactory getPrimarySessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addInitiator(new SharedSchemaConnectionProviderInitiatorAdapter())
                .addInitiator(new SharedSchemaContextProviderInitiator(this.getClass().getResource("/integration-tests-configuration-nonforeignkeyconstraint.yaml").getPath(), DefaultDecoratorContext.builder()
                        .withReplaceCharactersMap(MapBuilder.mapBuilder().put("{{template_schema_value}}", "public")
                                .put("{{template_user_grantee}}", "posmhib4-user").build()).build()))
//                .addInitiator(new CurrentTenantPreparedStatementSetterInitiator())
                .configure("hibernate.nonforeignkeyconstraint.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();

        SessionFactory factory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        return factory;
    }

    protected SessionFactory getSchemaCreatorSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addInitiator(new SharedSchemaContextProviderInitiator(this.getClass().getResource("/integration-tests-configuration-nonforeignkeyconstraint.yaml").getPath(), DefaultDecoratorContext.builder()
                        .withReplaceCharactersMap(MapBuilder.mapBuilder().put("{{template_schema_value}}", "public")
                                .put("{{template_user_grantee}}", "posmhib4-user").build()).build()))
                .configure("hibernate.nonforeignkeyconstraint.schema-creator.cfg.xml")
                .build();

        SessionFactory factory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        return factory;
    }

    @BeforeSuite(groups = "Integration tests for model without foreign keys constraints and with composite primary keys")
    public void prepareDatabase() {
        schemaCreatorSessionFactory = getSchemaCreatorSessionFactory();
        primarySessionFactory = getPrimarySessionFactory();
    }

    @BeforeClass
    public void openSession() throws SQLException, ValidationDatabaseOperationsException {
        ConnectionProvider connectionProvider = schemaCreatorSessionFactory.getSessionFactoryOptions()
                .getServiceRegistry()
                .getService(ConnectionProvider.class);

        // Get the DataSource from the connection provider
        schemaCreatorSession = schemaCreatorSessionFactory.openSession();
        SharedSchemaContextProvider sharedSchemaContextProvider = schemaCreatorSessionFactory.getSessionFactoryOptions()
                .getServiceRegistry()
                .getService(SharedSchemaContextProvider.class);
        sharedSchemaContext = sharedSchemaContextProvider.getSharedSchemaContext();
        try (Connection connection = connectionProvider.getConnection()) {
            this.databaseOperationExecutor.execute(connection, sharedSchemaContext.getSqlDefinitions(), LOG_ALL);
            this.databaseOperationExecutor.execute(connection, sharedSchemaContext.getSqlDefinitions(), CREATE);
            this.databaseOperationExecutor.execute(connection, sharedSchemaContext.getSqlDefinitions(), VALIDATE);
            connection.commit();
        }
    }

    @AfterClass
    public void dropRLSPolicy() {
        if (sharedSchemaContext != null) {
            ConnectionProvider connectionProvider = schemaCreatorSessionFactory.getSessionFactoryOptions()
                    .getServiceRegistry()
                    .getService(ConnectionProvider.class);
            try (Connection connection = connectionProvider.getConnection()) {
                this.databaseOperationExecutor.execute(connection, sharedSchemaContext.getSqlDefinitions(), DROP);
                this.databaseOperationExecutor.execute(connection, sharedSchemaContext.getSqlDefinitions(), LOG_ALL);
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ValidationDatabaseOperationsException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected Session openPrimarySession() {
        return primarySessionFactory.openSession();
    }

    @AfterClass
    public void closeSession() {
        try {
            if (schemaCreatorSession != null) {
                schemaCreatorSession.close();
            }
        } catch (Exception ex) {
            // do nothing
        }
        try {
            if (primarySession != null) {
                primarySession.close();
            }
        } catch (Exception ex) {
            // do nothing
        }
    }
}
