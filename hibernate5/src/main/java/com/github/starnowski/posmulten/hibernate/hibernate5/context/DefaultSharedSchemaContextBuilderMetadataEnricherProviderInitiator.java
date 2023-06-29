package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator implements StandardServiceInitiator<IDefaultSharedSchemaContextBuilderMetadataEnricherProvider> {
    @Override
    public IDefaultSharedSchemaContextBuilderMetadataEnricherProvider initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        DefaultSharedSchemaContextBuilderMetadataEnricherProvider result = new DefaultSharedSchemaContextBuilderMetadataEnricherProvider();
        result.initiateService(map, serviceRegistryImplementor);
        return result;
    }

    @Override
    public Class<IDefaultSharedSchemaContextBuilderMetadataEnricherProvider> getServiceInitiated() {
        return IDefaultSharedSchemaContextBuilderMetadataEnricherProvider.class;
    }
}
