package com.github.starnowski.posmulten.hibernate.core.schema;

import org.hibernate.boot.Metadata;
import org.hibernate.tool.schema.spi.ExecutionOptions;
import org.hibernate.tool.schema.spi.SchemaCreator;
import org.hibernate.tool.schema.spi.SourceDescriptor;
import org.hibernate.tool.schema.spi.TargetDescriptor;

public class SchemaCreatorWrapper implements SchemaCreator {

    private final SchemaCreator wrappedSchemaCreator;

    public SchemaCreatorWrapper(SchemaCreator wrappedSchemaCreator) {
        this.wrappedSchemaCreator = wrappedSchemaCreator;
    }

    public void doCreation(Metadata metadata, ExecutionOptions executionOptions, SourceDescriptor sourceDescriptor, TargetDescriptor targetDescriptor) {
        this.wrappedSchemaCreator.doCreation(metadata, executionOptions, sourceDescriptor, targetDescriptor);
        //TODO
    }

    SchemaCreator getWrappedSchemaCreator() {
        return wrappedSchemaCreator;
    }
}
