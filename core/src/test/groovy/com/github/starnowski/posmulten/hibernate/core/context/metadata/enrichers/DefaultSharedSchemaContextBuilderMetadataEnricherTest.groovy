package com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import org.hibernate.boot.Metadata
import org.hibernate.boot.model.relational.Database
import org.hibernate.boot.model.relational.Namespace
import org.hibernate.mapping.Table
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification

import java.util.stream.Collectors

class DefaultSharedSchemaContextBuilderMetadataEnricherTest extends Specification {

    def "should not be initialized by default" ()
    {
        when:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()

        then:
            !tested.isInitialized()
    }

    def "should be initialized after initialization with service"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()

        when:
            tested.initiateService(new HashMap(), null)

        then:
            tested != null
            tested.isInitialized()
    }

    def "should be initialized table metadata enrichres"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()
            tested.initiateService(new HashMap(), null)

        when:
            def results = tested.getEnrichers()

        then:
            results
            !results.isEmpty()
            results.stream().allMatch({ it.isInitialized()})

        and: "enrichers should have correct type"
            results.stream().map({ it.getClass() }).collect(Collectors.toList()) == [RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher.class]
    }

    def "should pass schema builder and metadata to table metadata enrichers"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()
            def tableMetadataEnricher = Mock(IDefaultSharedSchemaContextBuilderTableMetadataEnricher)
            def configurationMap = new HashMap()
            def sr = Mock(ServiceRegistryImplementor)
            tested.initiateService(configurationMap, sr)
            tested.setEnrichers([tableMetadataEnricher])
            DefaultSharedSchemaContextBuilder builder = Mock(DefaultSharedSchemaContextBuilder)
            Metadata metadata = Mock(Metadata)
            Namespace namespace = Mock(Namespace)
            Database database = Mock(Database)
            metadata.getDatabase() >> database
            database.getNamespaces() >> [namespace]
            Table table1 = Mock(Table)
            Table table2 = Mock(Table)
            namespace.getTables() >> [table1, table2]

        when:
            def result = tested.enrich(builder, metadata)

        then:
            result == builder
            1 * tableMetadataEnricher.enrich(builder, metadata, table1) >> builder
            1 * tableMetadataEnricher.enrich(builder, metadata, table2) >> builder
    }
}
