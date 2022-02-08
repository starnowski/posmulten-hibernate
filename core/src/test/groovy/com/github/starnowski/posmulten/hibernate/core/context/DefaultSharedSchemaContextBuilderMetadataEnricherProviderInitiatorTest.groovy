package com.github.starnowski.posmulten.hibernate.core.context

import spock.lang.Specification

class DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiatorTest extends Specification {

    def "should return default implementation of IDefaultSharedSchemaContextBuilderProvider type"()
    {
        given:
        def tested = new DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator()

        when:
        def result = tested.initiateService(new HashMap(), null)

        then:
        result != null
    }

    def "should return correct class"()
    {
        given:
        def tested = new DefaultSharedSchemaContextBuilderMetadataEnricherProviderInitiator()

        when:
        def result = tested.getServiceInitiated()

        then:
        result == IDefaultSharedSchemaContextBuilderMetadataEnricherProvider.class
    }
}
