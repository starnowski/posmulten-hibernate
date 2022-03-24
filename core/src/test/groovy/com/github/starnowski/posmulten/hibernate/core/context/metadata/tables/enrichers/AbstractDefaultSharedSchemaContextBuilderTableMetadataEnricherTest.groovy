package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification

abstract class AbstractDefaultSharedSchemaContextBuilderTableMetadataEnricherTest<T extends IDefaultSharedSchemaContextBuilderTableMetadataEnricher> extends Specification {

    def "should mark enricher as initialized after ivoking init"() {
        given:
            def tested = getTested()
            def map = getMap()
            def serviceRegistryImplementor = getServiceRegistryImplementor()

        when:
            tested.init(map, serviceRegistryImplementor)

        then:
            tested.isInitialized()
    }

    def "should mark enricher as not initialized before ivoking init"() {
        given:
            def tested = getTested()

        when:
            def result = tested.isInitialized()

        then:
            !result
    }

    abstract T getTested();

    abstract Map getMap();

    abstract ServiceRegistryImplementor getServiceRegistryImplementor();
}
