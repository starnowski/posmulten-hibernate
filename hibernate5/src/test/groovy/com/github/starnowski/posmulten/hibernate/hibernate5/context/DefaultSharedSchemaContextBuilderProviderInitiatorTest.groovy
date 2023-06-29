package com.github.starnowski.posmulten.hibernate.hibernate5.context

import spock.lang.Specification

class DefaultSharedSchemaContextBuilderProviderInitiatorTest extends Specification {

    def "should return default implementation of IDefaultSharedSchemaContextBuilderProvider type"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProviderInitiator()

        when:
            def result = tested.initiateService(new HashMap(), null)

        then:
            result != null
            result instanceof DefaultSharedSchemaContextBuilderProvider
    }

    def "should DefaultSharedSchemaContextBuilderProvider when property 'hibernate.posmulten.schema.builder.provider' has value 'full'"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProviderInitiator()
            Map map = new HashMap()
            map.put("hibernate.posmulten.schema.builder.provider", "full")

        when:
            def result = tested.initiateService(map, null)

        then:
            result != null
            result instanceof DefaultSharedSchemaContextBuilderProvider
    }

    def "should LightweightDefaultSharedSchemaContextBuilderProvider when property 'hibernate.posmulten.schema.builder.provider' has value 'lightweight'"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProviderInitiator()
            Map map = new HashMap()
            map.put("hibernate.posmulten.schema.builder.provider", "lightweight")

        when:
            def result = tested.initiateService(map, null)

        then:
            result != null
            result instanceof LightweightDefaultSharedSchemaContextBuilderProvider
    }

    def "should return correct class"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProviderInitiator()

        when:
            def result = tested.getServiceInitiated()

        then:
            result == IDefaultSharedSchemaContextBuilderProvider.class
    }
}
