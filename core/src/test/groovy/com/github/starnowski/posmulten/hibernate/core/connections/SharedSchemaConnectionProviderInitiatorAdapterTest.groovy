package com.github.starnowski.posmulten.hibernate.core.connections

import org.hibernate.MultiTenancyStrategy
import org.hibernate.boot.registry.selector.spi.StrategySelector
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
}
