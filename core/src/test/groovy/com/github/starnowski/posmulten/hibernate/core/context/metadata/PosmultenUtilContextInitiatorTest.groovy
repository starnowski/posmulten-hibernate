package com.github.starnowski.posmulten.hibernate.core.context.metadata

import spock.lang.Specification

class PosmultenUtilContextInitiatorTest extends Specification {

    def "should return default implementation of PosmultenUtilContext type"()
    {
        given:
            def tested = new PosmultenUtilContextInitiator()

        when:
            def result = tested.initiateService(null, null)

        then:
            result != null
            result.getTenantTablePropertiesResolver() != null
            result.getPersistentClassResolver() != null
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
}
