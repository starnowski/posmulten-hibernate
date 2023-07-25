package com.github.starnowski.posmulten.hibernate.hibernate6.connection;

import org.hibernate.engine.jdbc.connections.internal.ConnectionProviderInitiator;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;
import java.util.function.Supplier;

public class SharedSchemaConnectionProviderInitiatorAdapter extends ConnectionProviderInitiator {

    private final Supplier<DriverManagerConnectionProviderImpl> driverManagerConnectionProviderSupplier;

    public SharedSchemaConnectionProviderInitiatorAdapter() {
        this(DriverManagerConnectionProviderImpl::new);
    }

    public SharedSchemaConnectionProviderInitiatorAdapter(Supplier<DriverManagerConnectionProviderImpl> driverManagerConnectionProviderSupplier) {
        this.driverManagerConnectionProviderSupplier = driverManagerConnectionProviderSupplier;
    }

    @Override
    public Class<ConnectionProvider> getServiceInitiated() {
        return ConnectionProvider.class;
    }

    @Override
    public ConnectionProvider initiateService(Map configurationValues, ServiceRegistryImplementor registry) {
        DriverManagerConnectionProviderImpl connectionProvider = driverManagerConnectionProviderSupplier.get();
        connectionProvider.injectServices(registry);
        connectionProvider.configure(configurationValues);
        return connectionProvider;
    }
}
