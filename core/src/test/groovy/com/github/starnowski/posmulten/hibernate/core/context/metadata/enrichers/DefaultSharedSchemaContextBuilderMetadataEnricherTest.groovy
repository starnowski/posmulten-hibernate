package com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers

import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher
import spock.lang.Specification

import java.util.stream.Collectors

class DefaultSharedSchemaContextBuilderMetadataEnricherTest extends Specification {

    def "should not be initialized by default" ()
    {
        when:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()

        then:
            !tested.isInitialized()
    }

    def "should be initialized after initialization with service"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()

        when:
            tested.initiateService(new HashMap(), null)

        then:
            tested != null
            tested.isInitialized()
    }

    def "should be initialized table metadata enrichres"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()
            tested.initiateService(new HashMap(), null)

        when:
            def results = tested.getEnrichers()

        then:
            results
            !results.isEmpty()
            results.stream().allMatch({ it.isInitialized()})

        and: "enrichers should have correct type"
            results.stream().map({ it.getClass() }).collect(Collectors.toList()) == [RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher.class]

    }
    //TODO Test enriching builder with mocked enrichers (use setter)
}
