package com.github.starnowski.posmulten.hibernate.core.connections;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.engine.jndi.spi.JndiService;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SharedSchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider, ServiceRegistryAwareService {

    private ConnectionProvider connectionProvider;
    private JndiService jndiService;
    private DataSource dataSource;
    private ServiceRegistryImplementor serviceRegistry;

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
        //ConnectionProvider
        ConnectionProvider connectionProvider = serviceRegistry.getService(ConnectionProvider.class);
        //TODO init default tenant (tenant for any)
        if (connectionProvider != null) {
            this.connectionProvider = connectionProvider;
        }
        this.serviceRegistry = serviceRegistry;
//        final Object dataSourceConfigValue = serviceRegistry.getService(ConfigurationService.class)
//                .getSettings()
//                .get(AvailableSettings.DATASOURCE);
//        if (dataSourceConfigValue == null || !(dataSourceConfigValue instanceof String)) {
//            throw new HibernateException("Improper set up of DataSourceBasedMultiTenantConnectionProviderImpl");
//        }
//        final String jndiName = (String) dataSourceConfigValue;
//
//        jndiService = serviceRegistry.getService(JndiService.class);
//        if (jndiService == null) {
//            throw new HibernateException("Could not locate JndiService from DataSourceBasedMultiTenantConnectionProviderImpl");
//        }
//
//        final Object namedObject = jndiService.locate(jndiName);
//        if (namedObject == null) {
//            throw new HibernateException("JNDI name [" + jndiName + "] could not be resolved");
//        }
////TODO init default tenant (tenant for any)
//        if (namedObject instanceof DataSource) {
//            this.dataSource = (DataSource) namedObject;
//        } else if (namedObject instanceof Context) {
//            //TODO init default tenant (tenant for any)
//        } else {
//            throw new HibernateException(
//                    "Unknown object type [" + namedObject.getClass().getName() +
//                            "] found in JNDI location [" + jndiName + "]"
//            );
//        }
    }
}
