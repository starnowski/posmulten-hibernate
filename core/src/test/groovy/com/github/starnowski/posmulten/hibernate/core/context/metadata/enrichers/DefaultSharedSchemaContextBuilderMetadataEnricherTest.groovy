package com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers


import spock.lang.Specification

class DefaultSharedSchemaContextBuilderMetadataEnricherTest extends Specification {

    def "should not be initialized by default" ()
    {
        when:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()

        then:
            !tested.isInitialized()
    }
    //TODO Test initialization
    //TODO Test enriching builder with mocked enrichers (use setter)
}
