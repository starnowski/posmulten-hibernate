package com.github.starnowski.posmulten.hibernate.core.schema

import com.github.starnowski.posmulten.hibernate.core.context.DefaultSharedSchemaContextBuilderMetadataEnricherProvider
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderMetadataEnricherProvider
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderProvider
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
            SourceDescriptorFactory sourceDescriptorFactory = Mock(SourceDescriptorFactory)

        when:
            tested.doCreation(metadata, executionOptions, sourceDescriptor, targetDescriptor)

        then:
            1 * mockedServiceRegistry.getService(IDefaultSharedSchemaContextBuilderProvider) >> defaultSharedSchemaContextBuilderProvider
            1 * mockedServiceRegistry.getService(IDefaultSharedSchemaContextBuilderMetadataEnricherProvider) >> defaultSharedSchemaContextBuilderMetadataEnricherProvider
            1 * mockedServiceRegistry.getService(SourceDescriptorFactory) >> sourceDescriptorFactory

        then:
            1 * mockedSchemaCreator.doCreation(metadata, executionOptions, sourceDescriptor, targetDescriptor)
    }
}
