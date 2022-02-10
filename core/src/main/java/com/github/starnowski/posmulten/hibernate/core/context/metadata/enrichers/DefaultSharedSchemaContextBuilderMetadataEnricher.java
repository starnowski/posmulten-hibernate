package com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;

public class DefaultSharedSchemaContextBuilderMetadataEnricher implements IDefaultSharedSchemaContextBuilderMetadataEnricher {
    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata) {
        //TODO
        return null;
    }
}
