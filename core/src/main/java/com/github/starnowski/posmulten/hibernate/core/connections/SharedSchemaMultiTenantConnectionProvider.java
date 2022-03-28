package com.github.starnowski.posmulten.hibernate.core.connections;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderProvider;
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

    public Connection getAnyConnection() throws SQLException {
        return connectionProvider.getConnection();
    }

    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public Connection getConnection(String tenant) throws SQLException {
        final Connection connection = getAnyConnection();
        ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory factory = context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory();
        try {
            PreparedStatement statement = connection.prepareStatement(factory.returnPreparedStatementThatSetCurrentTenant());
            statement.setString(1, tenant);//TODO Try to resolve tenant column type (in case if type is different than string type)
            statement.execute();
        } catch (SQLException e) {
            //TODO sanitize message
            throw new HibernateException(
                    "Could not alter JDBC connection to specified tenant [" + tenant + "]",
                    e
            );
        }
        return connection;
    }

    public void releaseConnection(String tenant, Connection connection) throws SQLException {
        ISetCurrentTenantIdFunctionPreparedStatementInvocationFactory factory = context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory();
//        try {
//            PreparedStatement statement = connection.prepareStatement(factory.returnPreparedStatementThatSetCurrentTenant());
//            statement.setString(1, INVALID_TENANT_ID);//TODO Try to resolve tenant column type (in case if type is different than string type)
//            statement.execute();
//        } catch (SQLException e) {
//            throw new HibernateException(
//                    "Could not alter JDBC connection to specified tenant [" + tenant + "]",
//                    e
//            );
//        }
        connection.close();
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
        //TODO init default tenant (tenant for any)
        if (connectionProvider != null) {
            this.connectionProvider = connectionProvider;
        }
        IDefaultSharedSchemaContextBuilderProvider defaultSharedSchemaContextBuilderProvider = serviceRegistry.getService(IDefaultSharedSchemaContextBuilderProvider.class);
        try {
            this.context = defaultSharedSchemaContextBuilderProvider.get().build();
        } catch (SharedSchemaContextBuilderException e) {
            //TODO Thrown an exception
            e.printStackTrace();
        }
    }
}
