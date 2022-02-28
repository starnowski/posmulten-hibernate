package com.github.starnowski.posmulten.hibernate.core.context;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class DefaultSharedSchemaContextBuilderProviderInitiator implements StandardServiceInitiator<IDefaultSharedSchemaContextBuilderProvider> {
    @Override
    public IDefaultSharedSchemaContextBuilderProvider initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return new DefaultSharedSchemaContextBuilderProvider(map);
    }

    @Override
    public Class<IDefaultSharedSchemaContextBuilderProvider> getServiceInitiated() {
        return IDefaultSharedSchemaContextBuilderProvider.class;
    }
}
