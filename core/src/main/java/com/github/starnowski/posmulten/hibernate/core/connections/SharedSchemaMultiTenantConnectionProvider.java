package com.github.starnowski.posmulten.hibernate.core.connections;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.sql.Connection;
import java.sql.SQLException;

public class SharedSchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider, ServiceRegistryAwareService {
    public Connection getAnyConnection() throws SQLException {
        return null;
    }

    public void releaseAnyConnection(Connection connection) throws SQLException {

    }

    public Connection getConnection(String s) throws SQLException {
        return null;
    }

    public void releaseConnection(String s, Connection connection) throws SQLException {

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

    public void injectServices(ServiceRegistryImplementor serviceRegistryImplementor) {

    }
}
