package com.github.starnowski.posmulten.hibernate.hibernate5.context

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext
import spock.lang.Specification

class Hibernate5ContextSupplierTest extends Specification {

    def "should return hibernate context passed as constructor parameter"()
    {
        given:
            def hibernateContext = Mock(HibernateContext)
            def tested = new Hibernate5ContextSupplier(hibernateContext)

        when:
            def result = tested.get()

        then:
            result.is(hibernateContext)
    }
}
