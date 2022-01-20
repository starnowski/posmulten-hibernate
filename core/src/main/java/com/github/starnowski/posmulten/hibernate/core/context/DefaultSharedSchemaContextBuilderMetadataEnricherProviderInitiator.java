package com.github.starnowski.posmulten.hibernate.core.context;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator implements StandardServiceInitiator<DefaultSharedSchemaContextBuilderMetadataEnricherProvider> {
    @Override
    public DefaultSharedSchemaContextBuilderMetadataEnricherProvider initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return null;
    }

    @Override
    public Class<DefaultSharedSchemaContextBuilderMetadataEnricherProvider> getServiceInitiated() {
        return DefaultSharedSchemaContextBuilderMetadataEnricherProvider.class;
    }
}
