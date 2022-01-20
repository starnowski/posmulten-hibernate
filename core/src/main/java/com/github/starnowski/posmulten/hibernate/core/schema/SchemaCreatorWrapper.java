package com.github.starnowski.posmulten.hibernate.core.schema;

import com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderMetadataEnricherProvider;
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderProvider;
import org.hibernate.boot.Metadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaCreator;
import org.hibernate.tool.schema.spi.SourceDescriptor;
import org.hibernate.tool.schema.spi.TargetDescriptor;

public class SchemaCreatorWrapper implements SchemaCreator {

    private final SchemaCreator wrappedSchemaCreator;
    private final ServiceRegistry serviceRegistry;

    public SchemaCreatorWrapper(SchemaCreator wrappedSchemaCreator, ServiceRegistry serviceRegistry) {
        this.wrappedSchemaCreator = wrappedSchemaCreator;
        this.serviceRegistry = serviceRegistry;
    }

    public void doCreation(Metadata metadata, ExecutionOptions executionOptions, SourceDescriptor sourceDescriptor, TargetDescriptor targetDescriptor) {
        IDefaultSharedSchemaContextBuilderProvider defaultSharedSchemaContextBuilderProvider = serviceRegistry.getService(IDefaultSharedSchemaContextBuilderProvider.class);
        DefaultSharedSchemaContextBuilderMetadataEnricherProvider metadataEnricherProvider = serviceRegistry.getService(DefaultSharedSchemaContextBuilderMetadataEnricherProvider.class);
        metadataEnricherProvider.getEnrichers();
        this.wrappedSchemaCreator.doCreation(metadata, executionOptions, sourceDescriptor, targetDescriptor);
        //TODO

    }

    SchemaCreator getWrappedSchemaCreator() {
        return wrappedSchemaCreator;
    }
}
