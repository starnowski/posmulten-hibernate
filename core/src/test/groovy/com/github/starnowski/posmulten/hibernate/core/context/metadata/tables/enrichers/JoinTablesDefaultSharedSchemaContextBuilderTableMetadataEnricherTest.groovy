package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers

import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.CollectionResolver
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import org.hibernate.boot.Metadata
import org.hibernate.mapping.Collection
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Table
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification
import spock.lang.Unroll

class JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricherTest extends Specification {

    def tested = new JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher()

    def "should not enrich builder when collection can not be resolve for table"(){
        given:
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def persistentClassResolver = Mock(PersistentClassResolver)
            def collectionResolver = Mock(CollectionResolver)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getPersistentClassResolver() >> persistentClassResolver
            posmultenUtilContext.getCollectionResolver() >> collectionResolver
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            persistentClassResolver.resolve(metadata, table) >> null
            collectionResolver.resolve(metadata, table) >> null

            tested.init(null, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            0 * builder._

        and: "returned the same object of builder"
            result.is(builder)
    }

    def "should not enrich builder when persistentClass can be resolve for table"(){
        given:
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def persistentClassResolver = Mock(PersistentClassResolver)
            def collectionResolver = Mock(CollectionResolver)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getPersistentClassResolver() >> persistentClassResolver
            posmultenUtilContext.getCollectionResolver() >> collectionResolver
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)

            def collection = Mock(Collection)
            def persistentClass = Mock(PersistentClass)
            persistentClassResolver.resolve(metadata, table) >> persistentClass
            collectionResolver.resolve(metadata, table) >> collection

            tested.init(null, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            0 * builder._

        and: "returned the same object of builder"
            result.is(builder)
    }

    @Unroll
    def "should enrich builder for table #tableName with policy #rlsPolicyName"(){
        given:
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def persistentClassResolver = Mock(PersistentClassResolver)
            def collectionResolver = Mock(CollectionResolver)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getPersistentClassResolver() >> persistentClassResolver
            posmultenUtilContext.getCollectionResolver() >> collectionResolver
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            table.getName() >> tableName

            def collection = Mock(Collection)
            persistentClassResolver.resolve(metadata, table) >> null
            collectionResolver.resolve(metadata, table) >> collection

            def nameGenerator = Mock(NameGenerator)
            posmultenUtilContext.getNameGenerator() >> nameGenerator
            nameGenerator.generate("rls_policy_", table) >> rlsPolicyName

            tested.init(null, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            1 * builder.createRLSPolicyForTable(tableName, new HashMap<>(), null, rlsPolicyName)

        and: "returned the same object of builder"
            result.is(builder)

        where:
            tableName   |   rlsPolicyName
            "tabxxx"    |   "policy_x1"
            "roles"     |   "rls_policy"
    }
}
