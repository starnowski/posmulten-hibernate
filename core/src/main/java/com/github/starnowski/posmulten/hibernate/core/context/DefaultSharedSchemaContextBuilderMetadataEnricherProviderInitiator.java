package com.github.starnowski.posmulten.hibernate.core.context;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.ArrayList;
import java.util.Map;

public class DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator implements StandardServiceInitiator<IDefaultSharedSchemaContextBuilderMetadataEnricherProvider> {
    @Override
    public IDefaultSharedSchemaContextBuilderMetadataEnricherProvider initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return new DefaultSharedSchemaContextBuilderMetadataEnricherProvider(new ArrayList<>());
    }

    @Override
    public Class<IDefaultSharedSchemaContextBuilderMetadataEnricherProvider> getServiceInitiated() {
        return IDefaultSharedSchemaContextBuilderMetadataEnricherProvider.class;
    }
}
