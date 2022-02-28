package com.github.starnowski.posmulten.hibernate.core.schema;

import com.github.starnowski.posmulten.hibernate.core.schema.strategy.ISchemaCreatorStrategy;
import com.github.starnowski.posmulten.hibernate.core.schema.strategy.MetadataSchemaCreatorStrategy;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.boot.Metadata;
import org.hibernate.service.Service;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaCreator;
import org.hibernate.tool.schema.spi.SourceDescriptor;
import org.hibernate.tool.schema.spi.TargetDescriptor;


public class SchemaCreatorStrategyContext implements Service {

    private ISchemaCreatorStrategy metadataStrategy = new MetadataSchemaCreatorStrategy();

    public ISchemaCreatorStrategy getMetadataStrategy() {
        return this.metadataStrategy;
    }

    public void setMetadataStrategy(ISchemaCreatorStrategy strategy) {
        this.metadataStrategy = strategy;
    }

    public void doCreation(ISharedSchemaContext context, SchemaCreator schemaCreator, Metadata metadata, ExecutionOptions executionOptions, SourceDescriptor sourceDescriptor, TargetDescriptor targetDescriptor) {
        switch (sourceDescriptor.getSourceType()) {
            case METADATA:
                getMetadataStrategy().doCreation(context, schemaCreator, metadata, executionOptions, sourceDescriptor, targetDescriptor);
                break;
            default:
                //TODO
        }
    }
}
