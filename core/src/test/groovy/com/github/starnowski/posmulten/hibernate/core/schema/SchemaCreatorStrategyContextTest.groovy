package com.github.starnowski.posmulten.hibernate.core.schema

import com.github.starnowski.posmulten.hibernate.core.schema.strategy.ISchemaCreatorStrategy
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
import org.hibernate.boot.Metadata
import org.hibernate.tool.schema.spi.ExecutionOptions
import org.hibernate.tool.schema.spi.SchemaCreator
import org.hibernate.tool.schema.spi.SourceDescriptor
import org.hibernate.tool.schema.spi.TargetDescriptor
import spock.lang.Specification

import static org.hibernate.tool.schema.SourceType.METADATA

class SchemaCreatorStrategyContextTest extends Specification {

    def"should use metadata strategy"() {
        given:
            ISchemaCreatorStrategy metadataStrategy = Mock(ISchemaCreatorStrategy)
            ISharedSchemaContext context = Mock(ISharedSchemaContext)
            SchemaCreator schemaCreator = Mock(SchemaCreator)
            Metadata metadata = Mock(Metadata)
            ExecutionOptions executionOptions = Mock(ExecutionOptions)
            SourceDescriptor sourceDescriptor = Mock(SourceDescriptor)
            TargetDescriptor targetDescriptor = Mock(TargetDescriptor)
            def tested = new SchemaCreatorStrategyContext()
            tested.setMetadataStrategy(metadataStrategy)
        sourceDescriptor.getSourceType() >> METADATA

        when:
            tested.doCreation(context, schemaCreator, metadata, executionOptions, sourceDescriptor, targetDescriptor)

        then:
            1 * sourceDescriptor.getSourceType() >> METADATA
            1 * metadataStrategy.doCreation(context, schemaCreator, metadata, executionOptions, sourceDescriptor, targetDescriptor)

        then:
            0 * _._
    }
}
