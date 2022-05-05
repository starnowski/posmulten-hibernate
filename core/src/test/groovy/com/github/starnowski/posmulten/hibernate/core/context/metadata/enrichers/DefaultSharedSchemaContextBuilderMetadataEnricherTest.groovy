package com.github.starnowski.posmulten.hibernate.core.context.metadata.enrichers

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.ForeignKeySharedSchemaContextBuilderTableMetadataEnricher
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers.RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import org.hibernate.boot.Metadata
import org.hibernate.boot.model.relational.Database
import org.hibernate.boot.model.relational.Namespace
import org.hibernate.mapping.Table
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification
import spock.lang.Unroll

import java.util.stream.Collectors

import static com.github.starnowski.posmulten.hibernate.test.utils.MapBuilder.mapBuilder

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
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)

        when:
            tested.initiateService(new HashMap(), serviceRegistryImplementor)

        then:
            tested != null
            tested.isInitialized()
    }

    def "should be initialized table metadata enrichres"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            tested.initiateService(new HashMap(), serviceRegistryImplementor)

        when:
            def results = tested.getEnrichers()

        then:
            results
            !results.isEmpty()
            results.stream().allMatch({ it.isInitialized()})

        and: "enrichers should have correct type"
            results.stream().map({ it.getClass() }).collect(Collectors.toList()) == [RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, ForeignKeySharedSchemaContextBuilderTableMetadataEnricher.class, CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher.class]
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

    @Unroll
    def "should add additional enrichres specified by property 'hibernate.posmulten.metadata.table.additional.enrichers', expected enricher classes #expectedEnrichersClasses"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderMetadataEnricher()
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            tested.initiateService(configuration, serviceRegistryImplementor)

        when:
            def results = tested.getEnrichers()

        then:
            results
            !results.isEmpty()
            results.stream().allMatch({ it.isInitialized()})

        and: "enrichers should have correct type"
            results.stream().map({ it.getClass() }).collect(Collectors.toList()) == expectedEnrichersClasses

        where:
            configuration   ||  expectedEnrichersClasses
            mapBuilder().put("hibernate.posmulten.metadata.table.additional.enrichers", String.join(",", Type1.name)).build()        ||  [RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, ForeignKeySharedSchemaContextBuilderTableMetadataEnricher.class, CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher.class, Type1.class]
            mapBuilder().put("hibernate.posmulten.metadata.table.additional.enrichers", String.join(",", Type2.name)).build()        ||  [RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, ForeignKeySharedSchemaContextBuilderTableMetadataEnricher.class, CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher.class, Type2.class]
            mapBuilder().put("hibernate.posmulten.metadata.table.additional.enrichers", String.join(",", Type1.name, Type2.name)).build()        ||  [RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, ForeignKeySharedSchemaContextBuilderTableMetadataEnricher.class, CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher.class, Type1.class, Type2.class]
            mapBuilder().put("hibernate.posmulten.metadata.table.additional.enrichers", String.join(",", Type2.name, Type1.name)).build()        ||  [RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, ForeignKeySharedSchemaContextBuilderTableMetadataEnricher.class, CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher.class, Type2.class, Type1.class]
            mapBuilder().put("hibernate.posmulten.metadata.table.additional.enrichers", "").build()        ||  [RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher.class, ForeignKeySharedSchemaContextBuilderTableMetadataEnricher.class, CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher.class]
    }

    private static class Type1 implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

        private boolean initialized = false

        @Override
        DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
            return null
        }

        @Override
        void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
            this.initialized = true
        }

        @Override
        boolean isInitialized() {
            return initialized
        }
    }

    private static class Type2 implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

        private boolean initialized = false

        @Override
        DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
            return null
        }

        @Override
        void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
            this.initialized = true
        }

        @Override
        boolean isInitialized() {
            return initialized
        }
    }
}
