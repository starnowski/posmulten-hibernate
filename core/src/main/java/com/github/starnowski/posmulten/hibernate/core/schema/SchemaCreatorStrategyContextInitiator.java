package com.github.starnowski.posmulten.hibernate.core.schema;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class SchemaCreatorStrategyContextInitiator implements StandardServiceInitiator<SchemaCreatorStrategyContext> {
    @Override
    public SchemaCreatorStrategyContext initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return new SchemaCreatorStrategyContext();
    }

    @Override
    public Class<SchemaCreatorStrategyContext> getServiceInitiated() {
        return SchemaCreatorStrategyContext.class;
    }
}
