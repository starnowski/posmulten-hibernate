package com.github.starnowski.posmulten.hibernate.common.context

import com.github.starnowski.posmulten.hibernate.test.utils.MapBuilder
import spock.lang.Unroll

class PropertiesHibernateContextFactoryTest extends spock.lang.Specification {

    //hibernate.posmulten.tenant.id.default.id

    @Unroll
    def "should set correct default tenant id #defaultTenant for map: #map"(){
        given:
            def tested = new PropertiesHibernateContextFactory()

        when:
            def result = tested.build(map)

        then:
            result.getDefaultTenantId() == defaultTenant

        where:
            map || defaultTenant
        MapBuilder.mapBuilder().put("")
    }
}
