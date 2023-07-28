package com.github.starnowski.posmulten.hibernate.hibernate6.connection;

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
    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String s) {
        return connectionProvider;
    }

    public Connection getAnyConnection() throws SQLException {
        return connectionProvider.getConnection();
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
    }

    ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    ISharedSchemaContext getContext() {
        return context;
    }
}