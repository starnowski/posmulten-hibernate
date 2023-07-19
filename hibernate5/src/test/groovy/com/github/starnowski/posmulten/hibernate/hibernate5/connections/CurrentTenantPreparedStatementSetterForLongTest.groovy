package com.github.starnowski.posmulten.hibernate.hibernate5.connections

import spock.lang.Specification
import spock.lang.Unroll

import java.sql.PreparedStatement

class CurrentTenantPreparedStatementSetterForLongTest extends Specification {

    def tested = new CurrentTenantPreparedStatementSetterForLong()

    @Unroll
    def "should set first parameter with long '#value'" () {
        given:
            PreparedStatement preparedStatement = Mock(PreparedStatement)
            def tenant = Long.toString(value)

        when:
            tested.setup(preparedStatement, tenant)

        then:
            preparedStatement.setLong(1, value)

        where:
            value << [1l, 13l, new Long(43435), 13456999L]
    }
}
