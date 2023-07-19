package com.github.starnowski.posmulten.hibernate.hibernate5.schema.strategy

import com.github.starnowski.posmulten.hibernate.hibernate5.schema.SharedSchemaContextSourceInput
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
import org.hibernate.boot.Metadata
import org.hibernate.tool.schema.spi.*
import spock.lang.Specification

import static org.hibernate.tool.schema.SourceType.METADATA_THEN_SCRIPT

class MetadataSchemaCreatorStrategyTest extends Specification {

    def"should pass source with script source and type METADATA_THEN_SCRIPT to schema creator"(){
        given:
            ISharedSchemaContext context = Mock(ISharedSchemaContext)
            SchemaCreator schemaCreator = Mock(SchemaCreator)
            Metadata metadata = Mock(Metadata)
            ExecutionOptions executionOptions = Mock(ExecutionOptions)
            SourceDescriptor sourceDescriptor = Mock(SourceDescriptor)
            TargetDescriptor targetDescriptor = Mock(TargetDescriptor)
            SourceDescriptor passedSourceDescriptor = null
            def tested = new MetadataSchemaCreatorStrategy()

        when:
            tested.doCreation(context, schemaCreator, metadata, executionOptions, sourceDescriptor, targetDescriptor)

        then:
            1 * schemaCreator.doCreation(metadata, executionOptions, _, targetDescriptor) >> {
                arguments ->
                passedSourceDescriptor = (SourceDescriptor)arguments[2]
            }
            passedSourceDescriptor.getSourceType() == METADATA_THEN_SCRIPT
            ScriptSourceInput si = passedSourceDescriptor.getScriptSourceInput()
            si instanceof SharedSchemaContextSourceInput
    }
}
