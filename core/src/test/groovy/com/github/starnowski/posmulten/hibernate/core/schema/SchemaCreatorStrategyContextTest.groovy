package com.github.starnowski.posmulten.hibernate.core.schema

import com.github.starnowski.posmulten.hibernate.core.schema.strategy.ISchemaCreatorStrategy
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
import org.hibernate.boot.Metadata
import org.hibernate.tool.schema.spi.ExecutionOptions
import org.hibernate.tool.schema.spi.SourceDescriptor
import org.hibernate.tool.schema.spi.TargetDescriptor
import spock.lang.Specification

import static org.hibernate.tool.schema.SourceType.METADATA

class SchemaCreatorStrategyContextTest extends Specification {

    def"should use metadata strategy"() {
        given:
            ISchemaCreatorStrategy metadataStrategy = Mock(ISchemaCreatorStrategy)
            ISharedSchemaContext context = Mock(ISharedSchemaContext)
            Metadata metadata = Mock(Metadata)
            ExecutionOptions executionOptions = Mock(ExecutionOptions)
            SourceDescriptor sourceDescriptor = Mock(SourceDescriptor)
            TargetDescriptor targetDescriptor = Mock(TargetDescriptor)
            def tested = new SchemaCreatorStrategyContext()
            tested.setMetadataStrategy(metadataStrategy)

        when:
            tested.doCreation(context, metadata, executionOptions, sourceDescriptor, targetDescriptor)

        then:
            1 * sourceDescriptor.getSourceType() >> METADATA
            1 * metadataStrategy.doCreation(context, metadata, executionOptions, sourceDescriptor, targetDescriptor)
            0 * _._
    }
}
