package com.github.starnowski.posmulten.hibernate.common.context


import spock.lang.Unroll

import static com.github.starnowski.posmulten.hibernate.test.utils.MapBuilder.mapBuilder

class PropertiesHibernateContextFactoryTest extends spock.lang.Specification {

    @Unroll
    def "should set correct default tenant id #defaultTenant for map: #map"(){
        given:
            def tested = new PropertiesHibernateContextFactory()

        when:
            def result = tested.build(map)

        then:
            result.getDefaultTenantId() == defaultTenant

        where:
            map                                                                             || defaultTenant
            mapBuilder().put("hibernate.posmulten.tenant.id.default.id", "XXX").build()     || "XXX"
            mapBuilder().put("hibernate.posmulten.tenant.id.default.id", "test1").build()   || "test1"
            mapBuilder().build()                                                            || null
    }
}
