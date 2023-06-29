package com.github.starnowski.posmulten.hibernate.hibernate5.schema

import com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderMetadataEnricher
import com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderMetadataEnricherProvider
import com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderProvider
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import com.github.starnowski.posmulten.postgresql.core.context.ISharedSchemaContext
import com.github.starnowski.posmulten.postgresql.core.context.exceptions.SharedSchemaContextBuilderException
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
            1 * builder.build() >> sharedSchemaContext

        then:
            1 * schemaCreatorStrategyContext.doCreation(sharedSchemaContext, mockedSchemaCreator, metadata, executionOptions, sourceDescriptor, targetDescriptor)
    }

    def "should run wrapped schemaCreator component and rethrow exception when builder return an exception"() {
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
            def exception = Mock(SharedSchemaContextBuilderException)

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
            1 * builder.build() >> {throw exception}
            def ex = thrown(RuntimeException)
            ex.cause == exception

        then:
            0 * schemaCreatorStrategyContext._
    }
}
