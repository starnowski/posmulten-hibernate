package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class DefaultSharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {
    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        //TODO
        return null;
    }

    @Override
    public void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {

    }
}