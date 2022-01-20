package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.dialect.Dialect;

public interface IDefaultSharedSchemaContextBuilderMetadataEnricher {

    DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Dialect dialect);
}
