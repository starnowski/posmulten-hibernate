package com.github.starnowski.posmulten.hibernate.hibernate5.connections

import spock.lang.Specification
import spock.lang.Unroll

import java.sql.PreparedStatement

class CurrentTenantPreparedStatementSetterForStringTest extends Specification {

    def tested = new CurrentTenantPreparedStatementSetterForString()

    @Unroll
    def "should set first parameter with string '#value'" () {
        given:
            PreparedStatement preparedStatement = Mock(PreparedStatement)

        when:
            tested.setup(preparedStatement, value)

        then:
            preparedStatement.setString(1, value)

        where:
            value << ["tenant", "cur1", "12-dadf-ADFADF-cxx"]
    }
}
