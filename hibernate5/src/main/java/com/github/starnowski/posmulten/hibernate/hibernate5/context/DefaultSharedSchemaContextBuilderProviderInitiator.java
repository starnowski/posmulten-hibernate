package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.common.Properties.SCHEMA_BUILDER_PROVIDER;

public class DefaultSharedSchemaContextBuilderProviderInitiator implements StandardServiceInitiator<IDefaultSharedSchemaContextBuilderProvider> {
    @Override
    public IDefaultSharedSchemaContextBuilderProvider initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        if (map.containsKey(SCHEMA_BUILDER_PROVIDER)) {
            switch ((String) map.get(SCHEMA_BUILDER_PROVIDER)) {
                case "lightweight":
                    return new LightweightDefaultSharedSchemaContextBuilderProvider(map);
                case "full":
            }
        }
        return new DefaultSharedSchemaContextBuilderProvider(map);
    }

    @Override
    public Class<IDefaultSharedSchemaContextBuilderProvider> getServiceInitiated() {
        return IDefaultSharedSchemaContextBuilderProvider.class;
    }
}
