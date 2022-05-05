package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public interface IDefaultSharedSchemaContextBuilderMetadataEnricher {

    DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata);

    boolean isInitialized();

    void initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor);
}
