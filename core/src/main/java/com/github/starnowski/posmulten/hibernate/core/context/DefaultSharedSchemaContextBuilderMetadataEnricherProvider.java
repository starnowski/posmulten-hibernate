package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers.DefaultSharedSchemaContextBuilderMetadataEnricher;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultSharedSchemaContextBuilderMetadataEnricherProvider implements IDefaultSharedSchemaContextBuilderMetadataEnricherProvider {

    private List<IDefaultSharedSchemaContextBuilderMetadataEnricher> enrichers = null;
    private boolean initialized = false;

    public DefaultSharedSchemaContextBuilderMetadataEnricherProvider() {
    }

    public void initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        this.enrichers = new ArrayList<>();
        DefaultSharedSchemaContextBuilderMetadataEnricher enricher = new DefaultSharedSchemaContextBuilderMetadataEnricher();
        enricher.initiateService(map, serviceRegistryImplementor);
        this.enrichers.add(enricher);
        this.initialized = true;
    }

    public List<IDefaultSharedSchemaContextBuilderMetadataEnricher> getEnrichers() {
        return enrichers;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
