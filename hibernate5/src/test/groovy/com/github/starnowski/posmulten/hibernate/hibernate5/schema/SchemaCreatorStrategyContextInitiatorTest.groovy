package com.github.starnowski.posmulten.hibernate.hibernate5.schema


import spock.lang.Specification

class SchemaCreatorStrategyContextInitiatorTest extends Specification {

    def "should return default implementation of IDefaultSharedSchemaContextBuilderProvider type"()
    {
        given:
            def tested = new SchemaCreatorStrategyContextInitiator()

        when:
            def result = tested.initiateService(null, null)

        then:
            result != null
    }

    def "should return correct class"()
    {
        given:
            def tested = new SchemaCreatorStrategyContextInitiator()

        when:
            def result = tested.getServiceInitiated()

        then:
            result == SchemaCreatorStrategyContext.class
    }
}
