package com.github.starnowski.posmulten.hibernate.core.context.metadata

import spock.lang.Specification

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

    def "should return by default context with NameGenerator with default maxim length"()
    {
        given:
            def tested = new PosmultenUtilContextInitiator()

        when:
            def result = tested.initiateService(new HashMap(), null)

        then:
            result.getNameGenerator().getMaxLength() == MAXIMUM_IDENTIFIER_LENGTH
    }

    //TODO Set with property
}
