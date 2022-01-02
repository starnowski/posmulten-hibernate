package com.github.starnowski.posmulten.hibernate.core.schema

import org.hibernate.boot.registry.selector.spi.StrategySelector
import org.hibernate.service.spi.ServiceRegistryImplementor
import org.hibernate.tool.schema.internal.SchemaCreatorImpl
import org.hibernate.tool.schema.spi.SchemaFilterProvider
import spock.lang.Specification

class PosmultenSchemaManagementToolTest extends Specification {

    def "should return schema creator wrapper" ()
    {
        given:
            ServiceRegistryImplementor serviceRegistry = Mock(ServiceRegistryImplementor)
            StrategySelector selector = Mock(StrategySelector)
            SchemaFilterProvider filterProvider = Mock(SchemaFilterProvider)
            serviceRegistry.getService(StrategySelector) >> selector
            selector.resolveDefaultableStrategy(_, _, _) >> filterProvider
            def tested = new PosmultenSchemaManagementTool()
            tested.injectServices(serviceRegistry)

        when:
            def result = tested.getSchemaCreator(null)

        then:
            result.is(SchemaCreatorWrapper)
            SchemaCreatorWrapper schemaCreatorWrapper = (SchemaCreatorWrapper)result
            schemaCreatorWrapper.getWrappedSchemaCreator().is(SchemaCreatorImpl)
    }
}
