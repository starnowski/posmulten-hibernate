package com.github.starnowski.posmulten.hibernate.hibernate5.connections

import org.hibernate.MultiTenancyStrategy
import org.hibernate.boot.registry.selector.spi.StrategySelector
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification

class SharedSchemaConnectionProviderInitiatorAdapterTest extends Specification {

    def tested = new SharedSchemaConnectionProviderInitiatorAdapter()

    def "should create connection for shared schema strategy"()
    {
        given:
            Map configurationValues = new HashMap()
            configurationValues.put("hibernate.multiTenancy", MultiTenancyStrategy.SCHEMA.name())
            ServiceRegistryImplementor registry = Mock(ServiceRegistryImplementor)
            registry.getService(StrategySelector.class) >> Mock(StrategySelector)

        when:
            def result = tested.initiateService(configurationValues, registry)

        then:
            result != null
    }

    def "should return ConnectionProvider class"()
    {
        when:
            def result = tested.getServiceInitiated()

        then:
            result == ConnectionProvider
    }
}
