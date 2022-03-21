package com.github.starnowski.posmulten.hibernate.core.context.metadata

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.starnowski.posmulten.hibernate.core.Properties.MAXIMUM_IDENTIFIER_LENGTH

class PosmultenUtilContextInitiatorTest extends Specification {

    def "should return default implementation of PosmultenUtilContext type"()
    {
        given:
            def tested = new PosmultenUtilContextInitiator()

        when:
            def result = tested.initiateService(new HashMap(), null)

        then:
            result != null
            result.getTenantTablePropertiesResolver() != null
            result.getPersistentClassResolver() != null
            result.getNameGenerator() != null
            result.getCollectionResolver() != null
    }

    def "should return correct class"()
    {
        given:
            def tested = new PosmultenUtilContextInitiator()

        when:
            def result = tested.getServiceInitiated()

        then:
            result == PosmultenUtilContext.class
    }

    def "should return by default context with NameGenerator with default maximum length"()
    {
        given:
            def tested = new PosmultenUtilContextInitiator()

        when:
            def result = tested.initiateService(new HashMap(), null)

        then:
            result.getNameGenerator().getMaxLength() == MAXIMUM_IDENTIFIER_LENGTH
    }

    @Unroll
    def "should return context with NameGenerator with maximum length value specified by property hibernate.posmulten.maximum.identifier.length"()
    {
        given:
            def tested = new PosmultenUtilContextInitiator()
            def map = new HashMap()
            map.put("hibernate.posmulten.maximum.identifier.length", maximumLength)

        when:
            def result = tested.initiateService(map, null)

        then:
            result.getNameGenerator().getMaxLength() == Integer.parseInt(maximumLength)

        where:
            maximumLength << ["3", "133", "17"]
    }
}
