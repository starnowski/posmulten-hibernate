package com.github.starnowski.posmulten.hibernate.hibernate5.schema.strategy;

import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.boot.Metadata;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaCreator;
import org.hibernate.tool.schema.spi.SourceDescriptor;
import org.hibernate.tool.schema.spi.TargetDescriptor;

public interface ISchemaCreatorStrategy {

    void doCreation(ISharedSchemaContext context, SchemaCreator schemaCreator, Metadata metadata, ExecutionOptions executionOptions, SourceDescriptor sourceDescriptor, TargetDescriptor targetDescriptor);
}
