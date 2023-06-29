package com.github.starnowski.posmulten.hibernate.integration;

import com.github.starnowski.posmulten.hibernate.hibernate5.connections.CurrentTenantPreparedStatementSetterInitiator;
import com.github.starnowski.posmulten.hibernate.hibernate5.connections.SharedSchemaConnectionProviderInitiatorAdapter;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.DefaultSharedSchemaContextBuilderProviderInitiator;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.PosmultenUtilContextInitiator;
import com.github.starnowski.posmulten.hibernate.hibernate5.schema.SchemaCreatorStrategyContextInitiator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class AbstractBaseItTest {

    private static SessionFactory primarySessionFactory;
    private static SessionFactory schemaCreatorSessionFactory;
    protected Session schemaCreatorSession;
    protected Session primarySession;

    protected SessionFactory getPrimarySessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addInitiator(new SharedSchemaConnectionProviderInitiatorAdapter())
                .addInitiator(new DefaultSharedSchemaContextBuilderProviderInitiator())
                .addInitiator(new CurrentTenantPreparedStatementSetterInitiator())
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        SessionFactory factory = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        return factory;
    }

    protected SessionFactory getSchemaCreatorSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .addInitiator(new SchemaCreatorStrategyContextInitiator())
                .addInitiator(new DefaultSharedSchemaContextBuilderProviderInitiator())
                .addInitiator(new DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator())
                .addInitiator(new PosmultenUtilContextInitiator())
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
    public void openSession() {
        schemaCreatorSession = schemaCreatorSessionFactory.openSession();
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
