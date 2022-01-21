package com.github.starnowski.posmulten.hibernate.core.schema;

import com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderMetadataEnricherProvider;
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderProvider;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import com.github.starnowski.posmulten.postgresql.core.context.exceptions.SharedSchemaContextBuilderException;
import org.hibernate.boot.Metadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaCreator;
import org.hibernate.tool.schema.spi.SourceDescriptor;
import org.hibernate.tool.schema.spi.TargetDescriptor;

import java.util.List;

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
        List<IDefaultSharedSchemaContextBuilderMetadataEnricher> metadataEnrichers = metadataEnricherProvider.getEnrichers();
        SourceDescriptorFactory scriptSourceInputFactory = serviceRegistry.getService(SourceDescriptorFactory.class);
        DefaultSharedSchemaContextBuilder builder = defaultSharedSchemaContextBuilderProvider.get();
        for (IDefaultSharedSchemaContextBuilderMetadataEnricher enricher: metadataEnrichers) {
            builder = enricher.enrich(builder, metadata);
        }
        ISharedSchemaContext context = null;
        try {
            context = builder.build();
        } catch (SharedSchemaContextBuilderException e) {
            //TODO
            e.printStackTrace();
        }
        SourceDescriptor wrappedSourceDescriptor = scriptSourceInputFactory.build(context, sourceDescriptor, executionOptions.getConfigurationValues());
        this.wrappedSchemaCreator.doCreation(metadata, executionOptions, wrappedSourceDescriptor, targetDescriptor);
        //TODO

    }

    SchemaCreator getWrappedSchemaCreator() {
        return wrappedSchemaCreator;
    }
}
