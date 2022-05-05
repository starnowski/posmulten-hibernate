package com.github.starnowski.posmulten.hibernate.core.context

import com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers.DefaultSharedSchemaContextBuilderMetadataEnricher
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import org.hibernate.boot.Metadata
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification
import spock.lang.Unroll

import java.util.stream.Collectors

import static com.github.starnowski.posmulten.hibernate.test.utils.MapBuilder.mapBuilder

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

    @Unroll
    def "should add additional enrichres specified by property 'hibernate.posmulten.metadata.additional.enrichers', expected enricher classes #expectedEnrichersClasses"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricherProvider()
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            tested.initiateService(configuration, serviceRegistryImplementor)

        when:
            def results = tested.getEnrichers()

        then:
            results.stream().map({ it.getClass() }).collect(Collectors.toList()) == expectedEnrichersClasses

        where:
            configuration   ||  expectedEnrichersClasses
            mapBuilder().put("hibernate.posmulten.metadata.additional.enrichers", String.join(",", Type1.name)).build()        ||  [DefaultSharedSchemaContextBuilderMetadataEnricher.class, Type1.class]
            mapBuilder().put("hibernate.posmulten.metadata.additional.enrichers", String.join(",", Type2.name)).build()        ||  [DefaultSharedSchemaContextBuilderMetadataEnricher.class, Type2.class]
            mapBuilder().put("hibernate.posmulten.metadata.additional.enrichers", String.join(",", Type1.name, Type2.name)).build()        ||  [DefaultSharedSchemaContextBuilderMetadataEnricher.class, Type1.class, Type2.class]
            mapBuilder().put("hibernate.posmulten.metadata.additional.enrichers", String.join(",", Type2.name, Type1.name)).build()        ||  [DefaultSharedSchemaContextBuilderMetadataEnricher.class, Type2.class, Type1.class]
            mapBuilder().put("hibernate.posmulten.metadata.additional.enrichers", "").build()        ||  [DefaultSharedSchemaContextBuilderMetadataEnricher.class]
    }

    private static class Type1 implements IDefaultSharedSchemaContextBuilderMetadataEnricher {

        @Override
        DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata) {
            return null
        }

        @Override
        boolean isInitialized() {
            return false
        }
    }

    private static class Type2 implements IDefaultSharedSchemaContextBuilderMetadataEnricher {

        @Override
        DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata) {
            return null
        }

        @Override
        boolean isInitialized() {
            return false
        }
    }
}
