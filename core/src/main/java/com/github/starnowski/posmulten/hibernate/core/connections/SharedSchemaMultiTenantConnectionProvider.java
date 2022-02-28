package com.github.starnowski.posmulten.hibernate.core.connections;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.sql.Connection;
import java.sql.SQLException;

public class SharedSchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider, ServiceRegistryAwareService {

    private ConnectionProvider connectionProvider;

    public Connection getAnyConnection() throws SQLException {
        //TODO Set dummy tenant (for all)
        return connectionProvider.getConnection();
    }

    public void releaseAnyConnection(Connection connection) throws SQLException {

    }

    public Connection getConnection(String tenant) throws SQLException {
        return null;
    }

    public void releaseConnection(String tenant, Connection connection) throws SQLException {

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
    }
}
