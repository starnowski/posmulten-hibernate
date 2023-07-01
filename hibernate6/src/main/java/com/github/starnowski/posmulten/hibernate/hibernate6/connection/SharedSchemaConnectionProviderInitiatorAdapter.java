package com.github.starnowski.posmulten.hibernate.hibernate6.connection;

import org.hibernate.engine.jdbc.connections.internal.ConnectionProviderInitiator;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class SharedSchemaConnectionProviderInitiatorAdapter extends ConnectionProviderInitiator {


    @Override
    public Class<ConnectionProvider> getServiceInitiated() {
        return ConnectionProvider.class;
    }

    @Override
    public ConnectionProvider initiateService(Map configurationValues, ServiceRegistryImplementor registry) {
        //TODO add property
//        Map copiedMap = new HashMap(configurationValues);
//        copiedMap.remove(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER);
        return super.initiateService(configurationValues, registry);
    }
}
