package com.github.starnowski.posmulten.hibernate.core.schema;

import com.github.starnowski.posmulten.hibernate.core.schema.strategy.ISchemaCreatorStrategy;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.boot.Metadata;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SourceDescriptor;
import org.hibernate.tool.schema.spi.TargetDescriptor;

public class SchemaCreatorStrategyContext {

    public void setMetadataStrategy(ISchemaCreatorStrategy strategy)
    {
        //TODO
    }

    public ISchemaCreatorStrategy getMetadataStrategy()
    {
        //TODO
        return null;
    }

    public void doCreation(ISharedSchemaContext context, Metadata metadata, ExecutionOptions executionOptions, SourceDescriptor sourceDescriptor, TargetDescriptor targetDescriptor) {

    }
}
