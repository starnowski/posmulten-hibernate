package com.github.starnowski.posmulten.hibernate.hibernate5.schema;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class SourceDescriptorInitiator implements StandardServiceInitiator<SourceDescriptorFactory> {
    @Override
    public SourceDescriptorFactory initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return new SourceDescriptorFactory();
    }

    @Override
    public Class<SourceDescriptorFactory> getServiceInitiated() {
        return SourceDescriptorFactory.class;
    }
}
