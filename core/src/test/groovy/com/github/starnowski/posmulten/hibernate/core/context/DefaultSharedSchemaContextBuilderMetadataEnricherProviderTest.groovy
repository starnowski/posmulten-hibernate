package com.github.starnowski.posmulten.hibernate.core.context

import com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers.DefaultSharedSchemaContextBuilderMetadataEnricher
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification

import java.util.stream.Collectors

class DefaultSharedSchemaContextBuilderMetadataEnricherProviderTest extends Specification {

    def "should not be initialized by default" ()
    {
        when:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricherProvider()

        then:
            !tested.isInitialized()
    }

    def "should be initialized after initialization with service"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricherProvider()
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)

        when:
            tested.initiateService(new HashMap(), serviceRegistryImplementor)

        then:
            tested != null
            tested.isInitialized()
    }

    def "should by default return expected enrichers list"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricherProvider()
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            tested.initiateService(new HashMap(), serviceRegistryImplementor)

        when:
            def results = tested.getEnrichers()

        then:
            results.stream().map({ it.getClass() }).collect(Collectors.toList()) == [DefaultSharedSchemaContextBuilderMetadataEnricher.class]
            results.stream().allMatch({ it.isInitialized() })
    }
}
