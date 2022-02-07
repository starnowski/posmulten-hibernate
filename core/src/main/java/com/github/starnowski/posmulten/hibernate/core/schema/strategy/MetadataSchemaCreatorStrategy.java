package com.github.starnowski.posmulten.hibernate.core.schema.strategy;

import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.boot.Metadata;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaCreator;
import org.hibernate.tool.schema.spi.SourceDescriptor;
import org.hibernate.tool.schema.spi.TargetDescriptor;

public class MetadataSchemaCreatorStrategy implements ISchemaCreatorStrategy{
    @Override
    public void doCreation(ISharedSchemaContext context, SchemaCreator schemaCreator, Metadata metadata, ExecutionOptions executionOptions, SourceDescriptor sourceDescriptor, TargetDescriptor targetDescriptor) {

    }
}
