package com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class DefaultSharedSchemaContextBuilderMetadataEnricher implements IDefaultSharedSchemaContextBuilderMetadataEnricher {

    private boolean initialized = false;
    //TODO tableMetadataEnricher lists
    //TODO getter and setter for tableMetadataEnricher lists (package access)

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata) {
        //TODO
        return null;
    }

    public void initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        //TODO
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }
}
