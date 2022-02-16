package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public interface IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table);

    void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor);

    boolean isInitialized();
}
