package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers

import com.github.starnowski.posmulten.hibernate.common.context.metadata.tables.TenantTableProperties
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.*
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import org.hibernate.boot.Metadata
import org.hibernate.mapping.Collection
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Table
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Unroll

class JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricherTest extends AbstractDefaultSharedSchemaContextBuilderTableMetadataEnricherTest<JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher> {

    JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher tested = new JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher()

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
    def "should enrich builder for table #tableName with policy #rlsPolicyName, schema #schema"(){
        given:
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def persistentClassResolver = Mock(PersistentClassResolver)
            def collectionResolver = Mock(CollectionResolver)
            def tenantTablePropertiesResolver = Mock(TenantTablePropertiesResolver)
            def tableUtils = Mock(TableUtils)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getPersistentClassResolver() >> persistentClassResolver
            posmultenUtilContext.getCollectionResolver() >> collectionResolver
            posmultenUtilContext.getTableUtils() >> tableUtils
            posmultenUtilContext.getTenantTablePropertiesResolver() >> tenantTablePropertiesResolver
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            table.getName() >> tableName
            table.getSchema() >> schemaName

            def collection = Mock(Collection)
            persistentClassResolver.resolve(metadata, table) >> null
            collectionResolver.resolve(metadata, table) >> collection
            tableUtils.isAnyCollectionComponentIsTenantTable(collection, tenantTablePropertiesResolver, table, metadata) >> true

            def rlsPolicyHelper = Mock(RLSPolicyTableHelper)
            posmultenUtilContext.getRlsPolicyTableHelper() >> rlsPolicyHelper
            tested.init(null, serviceRegistryImplementor)
            TenantTableProperties tenantTableProperties = null


        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            1 * rlsPolicyHelper.enrichBuilderWithTableRLSPolicy(builder, table, _, posmultenUtilContext) >> {
                arguments ->
                    tenantTableProperties = (TenantTableProperties)arguments[2]
            }
            tenantTableProperties.getTable() == tableName
            tenantTableProperties.getTenantColumnName() == null
            tenantTableProperties.getPrimaryKeysColumnAndTypeMap() == [:]
            tenantTableProperties.getSchema() == schemaName

        and: "returned the same object of builder"
            result.is(builder)

        where:
            tableName   |   rlsPolicyName   |   schemaName
            "tabxxx"    |   "policy_x1"     |   null
            "roles"     |   "rls_policy"    |   "secondary"
    }

    @Unroll
    def "should not enrich builder for table #tableName with policy #rlsPolicyName, schema #schema when collection does not contains tenant tables"(){
        given:
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def persistentClassResolver = Mock(PersistentClassResolver)
            def collectionResolver = Mock(CollectionResolver)
            def tenantTablePropertiesResolver = Mock(TenantTablePropertiesResolver)
            def tableUtils = Mock(TableUtils)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getPersistentClassResolver() >> persistentClassResolver
            posmultenUtilContext.getCollectionResolver() >> collectionResolver
            posmultenUtilContext.getTableUtils() >> tableUtils
            posmultenUtilContext.getTenantTablePropertiesResolver() >> tenantTablePropertiesResolver
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            table.getName() >> tableName
            table.getSchema() >> schemaName

            def collection = Mock(Collection)
            persistentClassResolver.resolve(metadata, table) >> null
            collectionResolver.resolve(metadata, table) >> collection
            tableUtils.isAnyCollectionComponentIsTenantTable(collection, tenantTablePropertiesResolver, table, metadata) >> false

            def rlsPolicyHelper = Mock(RLSPolicyTableHelper)
            posmultenUtilContext.getRlsPolicyTableHelper() >> rlsPolicyHelper
            tested.init(null, serviceRegistryImplementor)
            TenantTableProperties tenantTableProperties = null


        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            0 * rlsPolicyHelper.enrichBuilderWithTableRLSPolicy(builder, table, _, posmultenUtilContext)
        and: "returned the same object of builder"
            result.is(builder)

        where:
            tableName   |   rlsPolicyName   |   schemaName
            "tabxxx"    |   "policy_x1"     |   null
            "roles"     |   "rls_policy"    |   "secondary"
    }

    @Override
    Map getMap() {
        return null
    }

    @Override
    ServiceRegistryImplementor getServiceRegistryImplementor() {
        return Mock(ServiceRegistryImplementor)
    }
}
