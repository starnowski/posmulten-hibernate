package com.github.starnowski.posmulten.hibernate.hibernate5.connections

import spock.lang.Specification
import spock.lang.Unroll

import java.sql.PreparedStatement
import java.sql.SQLException

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

    @Unroll
    def "should return implementation for custom type when property 'hibernate.posmulten.tenant.column.java.type' is set with value 'custom' and return class defined by property 'hibernate.posmulten.tenant.column.java.type.custom.resolver' #resolverClass"(){
        given:
            Map map = new HashMap()
            map.put("hibernate.posmulten.tenant.column.java.type", "custom")
            map.put("hibernate.posmulten.tenant.column.java.type.custom.resolver", resolverClass.getName())

        when:
            def result = tested.initiateService(map, null)

        then:
            result
            result.getClass().equals(resolverClass)

        where:
            resolverClass << [CurrentTenantPreparedStatementSetterType1.class, CurrentTenantPreparedStatementSetterType2.class]
    }

    def "should throw exception when when property 'hibernate.posmulten.tenant.column.java.type' is set with value 'custom' and the property 'hibernate.posmulten.tenant.column.java.type.custom.resolver' is not being set"(){
        given:
            Map map = new HashMap()
            map.put("hibernate.posmulten.tenant.column.java.type", "custom")

        when:
            tested.initiateService(map, null)

        then:
            def ex = thrown(RuntimeException)
            ex.message == "The property 'hibernate.posmulten.tenant.column.java.type' has value 'custom' but the 'hibernate.posmulten.tenant.column.java.type.custom.resolver' property is not set"
    }

    private final static class CurrentTenantPreparedStatementSetterType1 implements ICurrentTenantPreparedStatementSetter {
        @Override
        void setup(PreparedStatement statement, String tenant) throws SQLException {

        }
    }

    private final static class CurrentTenantPreparedStatementSetterType2 implements ICurrentTenantPreparedStatementSetter {
        @Override
        void setup(PreparedStatement statement, String tenant) throws SQLException {

        }
    }
}
