package com.github.starnowski.posmulten.hibernate.core.schema

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricher
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricherProvider
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderProvider
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
import org.hibernate.boot.Metadata
import org.hibernate.service.ServiceRegistry
import org.hibernate.tool.schema.spi.ExecutionOptions
import org.hibernate.tool.schema.spi.SchemaCreator
import org.hibernate.tool.schema.spi.SourceDescriptor
import org.hibernate.tool.schema.spi.TargetDescriptor
import spock.lang.Specification

class SchemaCreatorWrapperTest extends Specification {
    
    def "should run wrapped schemaCreator component"() {
        given:
            SchemaCreator mockedSchemaCreator = Mock(SchemaCreator)
            ServiceRegistry mockedServiceRegistry = Mock(ServiceRegistry)
            def tested = new SchemaCreatorWrapper(mockedSchemaCreator, mockedServiceRegistry)
            Metadata metadata = Mock(Metadata)
            ExecutionOptions executionOptions = Mock(ExecutionOptions)
            SourceDescriptor sourceDescriptor = Mock(SourceDescriptor)
            TargetDescriptor targetDescriptor = Mock(TargetDescriptor)
            IDefaultSharedSchemaContextBuilderProvider defaultSharedSchemaContextBuilderProvider = Mock(IDefaultSharedSchemaContextBuilderProvider)
            IDefaultSharedSchemaContextBuilderMetadataEnricherProvider defaultSharedSchemaContextBuilderMetadataEnricherProvider = Mock(IDefaultSharedSchemaContextBuilderMetadataEnricherProvider)
            IDefaultSharedSchemaContextBuilderMetadataEnricher enricher = Mock(IDefaultSharedSchemaContextBuilderMetadataEnricher)
            SchemaCreatorStrategyContext schemaCreatorStrategyContext = Mock(SchemaCreatorStrategyContext)
            DefaultSharedSchemaContextBuilder builder = Mock(DefaultSharedSchemaContextBuilder)
            ISharedSchemaContext sharedSchemaContext = Mock(ISharedSchemaContext)

        when:
            tested.doCreation(metadata, executionOptions, sourceDescriptor, targetDescriptor)

        then:
            1 * mockedServiceRegistry.getService(IDefaultSharedSchemaContextBuilderProvider) >> defaultSharedSchemaContextBuilderProvider
            1 * mockedServiceRegistry.getService(IDefaultSharedSchemaContextBuilderMetadataEnricherProvider) >> defaultSharedSchemaContextBuilderMetadataEnricherProvider
            1 * mockedServiceRegistry.getService(SchemaCreatorStrategyContext) >> schemaCreatorStrategyContext

        then:
            1 * defaultSharedSchemaContextBuilderProvider.get() >> builder
            1 * enricher.enrich(builder, metadata) >> builder
            1 * defaultSharedSchemaContextBuilderMetadataEnricherProvider.getEnrichers() >> [enricher]
            1 * mockedSchemaCreator.doCreation(metadata, executionOptions, sourceDescriptor, targetDescriptor)
            1 * builder.build() >> sharedSchemaContext

        then:
            1 * schemaCreatorStrategyContext.doCreation(sharedSchemaContext, mockedSchemaCreator, metadata, executionOptions, sourceDescriptor, targetDescriptor)
    }
}
