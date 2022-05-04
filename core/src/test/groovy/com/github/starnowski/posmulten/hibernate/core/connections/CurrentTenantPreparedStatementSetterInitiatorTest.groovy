package com.github.starnowski.posmulten.hibernate.core.connections

import spock.lang.Specification

class CurrentTenantPreparedStatementSetterInitiatorTest extends Specification {

    def tested = new CurrentTenantPreparedStatementSetterInitiator()

    def "should return ICurrentTenantPreparedStatementSetter class"(){
        when:
            def result = tested.getServiceInitiated()

        then:
            result == ICurrentTenantPreparedStatementSetter.class
    }

    def "should by default return implementation for string type"(){
        given:
            Map map = new HashMap()

        when:
            def result = tested.initiateService(map, null)

        then:
            result
            result instanceof CurrentTenantPreparedStatementSetterForString
    }

    def "should return implementation for string type when property 'hibernate.posmulten.tenant.column.java.type' is set with value 'string'"(){
        given:
            Map map = new HashMap()
            map.put("hibernate.posmulten.tenant.column.java.type", "string")

        when:
            def result = tested.initiateService(map, null)

        then:
            result
            result instanceof CurrentTenantPreparedStatementSetterForString
    }

    def "should return implementation for long type when property 'hibernate.posmulten.tenant.column.java.type' is set with value 'long'"(){
        given:
            Map map = new HashMap()
            map.put("hibernate.posmulten.tenant.column.java.type", "long")

        when:
            def result = tested.initiateService(map, null)

        then:
            result
            result instanceof CurrentTenantPreparedStatementSetterForLong
    }
}
