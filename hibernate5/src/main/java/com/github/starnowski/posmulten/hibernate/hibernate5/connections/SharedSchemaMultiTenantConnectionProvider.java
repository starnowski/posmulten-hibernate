package com.github.starnowski.posmulten.hibernate.hibernate5.connections;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.Hibernate5ContextSupplier;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderProvider;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import com.github.starnowski.posmulten.postgresql.core.context.exceptions.SharedSchemaContextBuilderException;
import com.github.starnowski.posmulten.postgresql.core.rls.function.ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SharedSchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider, ServiceRegistryAwareService {

    private ConnectionProvider connectionProvider;
    private ISharedSchemaContext context;
    private ICurrentTenantPreparedStatementSetter currentTenantPreparedStatementSetter;
    private String defaultTenantId;

    public Connection getAnyConnection() throws SQLException {
        Connection connection = connectionProvider.getConnection();
        if (defaultTenantId != null){
            ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory factory = context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory();
            try {
                PreparedStatement statement = connection.prepareStatement(factory.returnPreparedStatementThatSetCurrentTenant());
                currentTenantPreparedStatementSetter.setup(statement, defaultTenantId);
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
            currentTenantPreparedStatementSetter.setup(statement, tenant);
            statement.execute();
        } catch (SQLException e) {
            //TODO sanitize message with tenant id
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
        IDefaultSharedSchemaContextBuilderProvider defaultSharedSchemaContextBuilderProvider = serviceRegistry.getService(IDefaultSharedSchemaContextBuilderProvider.class);
        try {
            this.context = defaultSharedSchemaContextBuilderProvider.get().build();
        } catch (SharedSchemaContextBuilderException e) {
            throw new RuntimeException(e);
        }
        this.currentTenantPreparedStatementSetter = serviceRegistry.getService(ICurrentTenantPreparedStatementSetter.class);
        Hibernate5ContextSupplier hibernate5ContextSupplier = serviceRegistry.getService(Hibernate5ContextSupplier.class);
        if (hibernate5ContextSupplier != null){
            HibernateContext hibernateContext = hibernate5ContextSupplier.get();
            defaultTenantId = hibernateContext.getDefaultTenantId();
        }
    }
}
