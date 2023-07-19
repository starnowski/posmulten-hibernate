package com.github.starnowski.posmulten.hibernate.hibernate5.connections;

import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.internal.ConnectionProviderInitiator;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.HashMap;
import java.util.Map;

public class SharedSchemaConnectionProviderInitiatorAdapter extends ConnectionProviderInitiator {


    @Override
    public Class<ConnectionProvider> getServiceInitiated() {
        return ConnectionProvider.class;
    }

    @Override
    public ConnectionProvider initiateService(Map configurationValues, ServiceRegistryImplementor registry) {
        //TODO add property
        Map copiedMap = new HashMap(configurationValues);
        copiedMap.remove(Environment.MULTI_TENANT);
        return super.initiateService(copiedMap, registry);
    }
}
