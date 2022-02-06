package com.github.starnowski.posmulten.hibernate.core.context;

import org.hibernate.service.Service;

import java.util.List;

public class DefaultSharedSchemaContextBuilderMetadataEnricherProvider implements IDefaultSharedSchemaContextBuilderMetadataEnricherProvider {

    private final List<IDefaultSharedSchemaContextBuilderMetadataEnricher> enrichers;

    public DefaultSharedSchemaContextBuilderMetadataEnricherProvider(List<IDefaultSharedSchemaContextBuilderMetadataEnricher> enrichers) {
        this.enrichers = enrichers;
    }

    public List<IDefaultSharedSchemaContextBuilderMetadataEnricher> getEnrichers() {
        return enrichers;
    }
}
