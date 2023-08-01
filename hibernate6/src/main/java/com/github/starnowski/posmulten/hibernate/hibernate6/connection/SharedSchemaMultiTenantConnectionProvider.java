package com.github.starnowski.posmulten.hibernate.hibernate6.connection;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import com.github.starnowski.posmulten.hibernate.hibernate6.context.Hibernate6ContextSupplier;
import com.github.starnowski.posmulten.hibernate.hibernate6.context.SharedSchemaContextProvider;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import com.github.starnowski.posmulten.postgresql.core.rls.function.ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SharedSchemaMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider implements ServiceRegistryAwareService {

    private ConnectionProvider connectionProvider;
    private ISharedSchemaContext context;
    private String defaultTenantId;

    void setDefaultTenantId(String defaultTenantId) {
        this.defaultTenantId = defaultTenantId;
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String s) {
        return connectionProvider;
    }

    public Connection getAnyConnection() throws SQLException {
        Connection connection = connectionProvider.getConnection();
        if (defaultTenantId != null) {
            ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory factory = context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory();
            try {
                PreparedStatement statement = connection.prepareStatement(factory.returnPreparedStatementThatSetCurrentTenant());
                statement.setString(1, defaultTenantId);
                statement.execute();
            } catch (SQLException e) {
                //TODO sanitize message with tenant id
                throw new HibernateException(
                        "Could not alter JDBC connection to specified (default) tenant",
                        e
                );
            }
        }
        return connection;
    }

    public void releaseAnyConnection(Connection connection) throws SQLException {
        connectionProvider.closeConnection(connection);
    }

    public Connection getConnection(String tenant) throws SQLException {
        final Connection connection = getAnyConnection();
        ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory factory = context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory();
        try {
            PreparedStatement statement = connection.prepareStatement(factory.returnPreparedStatementThatSetCurrentTenant());
            statement.setString(1, tenant);
            statement.execute();
        } catch (SQLException e) {
            //TODO sanitize message
//            throw new HibernateException(
//                    "Could not alter JDBC connection to specified tenant [" + tenant + "]",
//                    e
//            );
            throw new HibernateException(
                    "Could not alter JDBC connection to specified tenant",
                    e
            );
        }
        return connection;
    }

    public void releaseConnection(String tenant, Connection connection) throws SQLException {
        connectionProvider.closeConnection(connection);
    }

    public boolean supportsAggressiveRelease() {
        return false;
    }

    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
        ConnectionProvider connectionProvider = serviceRegistry.getService(ConnectionProvider.class);
        if (connectionProvider != null) {
            this.connectionProvider = connectionProvider;
        }
        SharedSchemaContextProvider sharedSchemaContextProvider = serviceRegistry.getService(SharedSchemaContextProvider.class);
        this.context = sharedSchemaContextProvider.getSharedSchemaContext();
        Hibernate6ContextSupplier hibernate5ContextSupplier = serviceRegistry.getService(Hibernate6ContextSupplier.class);
        if (hibernate5ContextSupplier != null) {
            HibernateContext hibernateContext = hibernate5ContextSupplier.get();
            defaultTenantId = hibernateContext.getDefaultTenantId();
        }
    }

    ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    void setConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    ISharedSchemaContext getContext() {
        return context;
    }

    void setContext(ISharedSchemaContext context) {
        this.context = context;
    }
}