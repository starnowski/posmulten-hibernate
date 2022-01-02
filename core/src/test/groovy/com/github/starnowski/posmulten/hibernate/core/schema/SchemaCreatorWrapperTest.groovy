package com.github.starnowski.posmulten.hibernate.core.schema

import org.hibernate.boot.Metadata
import org.hibernate.tool.schema.spi.ExecutionOptions
import org.hibernate.tool.schema.spi.SchemaCreator
import org.hibernate.tool.schema.spi.SourceDescriptor
import org.hibernate.tool.schema.spi.TargetDescriptor
import spock.lang.Specification

class SchemaCreatorWrapperTest extends Specification {
    
    def "should run wrapped schemaCreator component"() {
        given:
            SchemaCreator mockedSchemaCreator = Mock(SchemaCreator)
            def tested = new SchemaCreatorWrapper(mockedSchemaCreator)
            Metadata metadata = Mock(Metadata)
            ExecutionOptions executionOptions = Mock(ExecutionOptions)
            SourceDescriptor sourceDescriptor = Mock(SourceDescriptor)
            TargetDescriptor targetDescriptor = Mock(TargetDescriptor)

        when:
            tested.doCreation(metadata, executionOptions, sourceDescriptor, targetDescriptor)

        then:
            1 * mockedSchemaCreator.doCreation(metadata, executionOptions, sourceDescriptor, targetDescriptor)
    }
}
