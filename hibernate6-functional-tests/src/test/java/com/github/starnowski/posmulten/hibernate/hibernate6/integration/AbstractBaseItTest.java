package com.github.starnowski.posmulten.hibernate.hibernate6.integration;

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
import org.postgresql.jdbc.PgConnection;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.github.starnowski.posmulten.postgresql.core.db.DatabaseOperationType.*;

public class AbstractBaseItTest {

    private static SessionFactory primarySessionFactory;
    private static SessionFactory schemaCreatorSessionFactory;
    protected Session schemaCreatorSession;
    protected Session primarySession;
    private DatabaseOperationExecutor databaseOperationExecutor = new DatabaseOperationExecutor();
    private DataSource schemaCreatorDataSource;
    private ISharedSchemaContext sharedSchemaContext;

    protected SessionFactory getPrimarySessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addInitiator(new SharedSchemaConnectionProviderInitiatorAdapter())
                .addInitiator(new SharedSchemaContextProviderInitiator(this.getClass().getResource("/integration-tests-configuration.yaml").getPath(), DefaultDecoratorContext.builder()
                        .withReplaceCharactersMap(MapBuilder.mapBuilder().put("{{template_schema_value}}", "public")
                                .put("{{template_user_grantee}}", "posmhib4-user").build()).build()))
//                .addInitiator(new CurrentTenantPreparedStatementSetterInitiator())
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        SessionFactory factory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        return factory;
    }

    protected SessionFactory getSchemaCreatorSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addInitiator(new SharedSchemaContextProviderInitiator(this.getClass().getResource("/integration-tests-configuration.yaml").getPath(), DefaultDecoratorContext.builder()
                        .withReplaceCharactersMap(MapBuilder.mapBuilder().put("{{template_schema_value}}", "public")
                                .put("{{template_user_grantee}}", "posmhib4-user").build()).build()))
                .configure("hibernate.schema-creator.cfg.xml")
                .build();

        SessionFactory factory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        return factory;
    }

    @BeforeSuite(groups = "Integration tests")
    public void prepareDatabase() {
        schemaCreatorSessionFactory = getSchemaCreatorSessionFactory();
        primarySessionFactory = getPrimarySessionFactory();
    }

    @BeforeClass
    public void openSession() throws ValidationDatabaseOperationsException, SQLException {
        ConnectionProvider connectionProvider = schemaCreatorSessionFactory.getSessionFactoryOptions()
                .getServiceRegistry()
                .getService(ConnectionProvider.class);

        // Get the DataSource from the connection provider
        org.postgresql.jdbc.PgConnection pgConnection = (PgConnection) connectionProvider.getConnection();
        schemaCreatorSession = schemaCreatorSessionFactory.openSession();
        SharedSchemaContextProvider sharedSchemaContextProvider = schemaCreatorSessionFactory.getSessionFactoryOptions()
                .getServiceRegistry()
                .getService(SharedSchemaContextProvider.class);
        sharedSchemaContext = sharedSchemaContextProvider.getSharedSchemaContext();
        this.databaseOperationExecutor.execute(schemaCreatorDataSource, sharedSchemaContext.getSqlDefinitions(), CREATE);
        this.databaseOperationExecutor.execute(schemaCreatorDataSource, sharedSchemaContext.getSqlDefinitions(), LOG_ALL);
        this.databaseOperationExecutor.execute(schemaCreatorDataSource, sharedSchemaContext.getSqlDefinitions(), VALIDATE);
    }

    protected Session openPrimarySession() {
        return primarySessionFactory.openSession();
    }

    @AfterClass
    public void dropRLSPolicy(){
        if (schemaCreatorDataSource != null && sharedSchemaContext != null){
            try {
                this.databaseOperationExecutor.execute(schemaCreatorDataSource, sharedSchemaContext.getSqlDefinitions(), DROP);
                this.databaseOperationExecutor.execute(schemaCreatorDataSource, sharedSchemaContext.getSqlDefinitions(), LOG_ALL);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ValidationDatabaseOperationsException e) {
                throw new RuntimeException(e);
            }
        }
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
