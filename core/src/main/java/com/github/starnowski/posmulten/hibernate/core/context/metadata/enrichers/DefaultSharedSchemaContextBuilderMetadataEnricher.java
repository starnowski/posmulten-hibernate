package com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultSharedSchemaContextBuilderMetadataEnricher implements IDefaultSharedSchemaContextBuilderMetadataEnricher {

    private boolean initialized = false;

    private List<RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher> enrichers;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata) {
        //TODO
        return null;
    }

    public void initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        this.enrichers = new ArrayList<>();
        enrichers.add(new RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher());
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    List<RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher> getEnrichers() {
        return enrichers;
    }

    void setEnrichers(List<RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher> enrichers) {
        this.enrichers = enrichers;
    }
}
