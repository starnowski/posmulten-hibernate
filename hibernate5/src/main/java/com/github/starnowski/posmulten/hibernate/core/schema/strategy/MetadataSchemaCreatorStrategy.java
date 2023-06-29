package com.github.starnowski.posmulten.hibernate.core.schema.strategy;

import com.github.starnowski.posmulten.hibernate.core.schema.SharedSchemaContextSourceInput;
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext;
import org.hibernate.boot.Metadata;
import org.hibernate.tool.schema.SourceType;
import org.hibernate.tool.schema.spi.*;

import static org.hibernate.tool.schema.SourceType.METADATA_THEN_SCRIPT;

public class MetadataSchemaCreatorStrategy implements ISchemaCreatorStrategy{
    @Override
    public void doCreation(ISharedSchemaContext context, SchemaCreator schemaCreator, Metadata metadata, ExecutionOptions executionOptions, SourceDescriptor sourceDescriptor, TargetDescriptor targetDescriptor) {
        SharedSchemaContextSourceInput sharedSchemaContextSourceInput = new SharedSchemaContextSourceInput(context);
        SourceDescriptor newSourceDescriptor = new SourceDescriptor() {
            @Override
            public SourceType getSourceType() {
                return METADATA_THEN_SCRIPT;
            }

            @Override
            public ScriptSourceInput getScriptSourceInput() {
                return sharedSchemaContextSourceInput;
            }
        };
        schemaCreator.doCreation(metadata, executionOptions, newSourceDescriptor, targetDescriptor);
    }
}
