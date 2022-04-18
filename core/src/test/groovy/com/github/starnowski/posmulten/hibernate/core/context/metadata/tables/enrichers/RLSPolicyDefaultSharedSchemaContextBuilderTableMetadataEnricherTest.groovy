package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers

import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.*
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import com.github.starnowski.posmulten.postgresql.core.context.SharedSchemaContextRequest
import com.github.starnowski.posmulten.postgresql.core.context.TableKey
import org.hibernate.boot.Metadata
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Table
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Unroll

class RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricherTest extends AbstractDefaultSharedSchemaContextBuilderTableMetadataEnricherTest<RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher> {

    RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher tested = new RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher()

    def "should not enrich builder when persistentClass can not be resolve for table"(){
        given:
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def persistentClassResolver = Mock(PersistentClassResolver)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getPersistentClassResolver() >> persistentClassResolver
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            persistentClassResolver.resolve(metadata, table) >> null

            tested.init(null, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            0 * builder._

        and: "returned the same object of builder"
            result.is(builder)
    }

    def "should not enrich builder when table properties can not be resolve for table"(){
        given:
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def persistentClassResolver = Mock(PersistentClassResolver)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getPersistentClassResolver() >> persistentClassResolver
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            def persistentClass = Mock(PersistentClass)
            persistentClassResolver.resolve(metadata, table) >> persistentClass
            def tenantTablePropertiesResolver = Mock(TenantTablePropertiesResolver)
            posmultenUtilContext.getTenantTablePropertiesResolver() >> tenantTablePropertiesResolver
            tenantTablePropertiesResolver.resolve(persistentClass, table, metadata) >> null

            tested.init(null, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            0 * builder._

        and: "returned the same object of builder"
            result.is(builder)
    }

    @Unroll
    def "should enrich builder with rls policy #policyName for table #tableName, schema #schema, tenant column #tenantColumnName and pass primary key columns #primaryKeysColumnAndTypeMap"(){
        given:
            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def persistentClassResolver = Mock(PersistentClassResolver)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getPersistentClassResolver() >> persistentClassResolver
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            def persistentClass = Mock(PersistentClass)
            persistentClassResolver.resolve(metadata, table) >> persistentClass
            def tenantTablePropertiesResolver = Mock(TenantTablePropertiesResolver)
            posmultenUtilContext.getTenantTablePropertiesResolver() >> tenantTablePropertiesResolver
            def tenantTableProperties = Mock(TenantTableProperties)
            tenantTableProperties.getTable() >> tableName
            tenantTableProperties.getPrimaryKeysColumnAndTypeMap() >> primaryKeysColumnAndTypeMap
            tenantTableProperties.getTenantColumnName() >> tenantColumnName
            tenantTableProperties.getSchema() >> schema
            tenantTablePropertiesResolver.resolve(persistentClass, table, metadata) >> tenantTableProperties
            def nameGenerator = Mock(NameGenerator)
            nameGenerator.generate("rls_policy_", table) >> policyName
            posmultenUtilContext.getNameGenerator() >> nameGenerator

            def requestCopy = Mock(SharedSchemaContextRequest)
            builder.getSharedSchemaContextRequestCopy() >> requestCopy
            requestCopy.resolveTenantColumnByTableKey(new TableKey(tableName, schema)) >> tenantColumnName

            def tableUtils = Mock(TableUtils)
            posmultenUtilContext.getTableUtils() >> tableUtils
            tableUtils.hasColumnWithName(table, tenantColumnName) >> false

            tested.init(null, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            1 * builder.createRLSPolicyForTable(tableName, primaryKeysColumnAndTypeMap, tenantColumnName, policyName)

        and: "create tenant column when column does not yet exist for table"
            1 * builder.createTenantColumnForTable(tableName)

        and: "returned the same object of builder"
            result.is(builder)

        where:
            tableName           |   schema      |   primaryKeysColumnAndTypeMap             |   tenantColumnName    |   policyName
            "tab1"              |   null        |  [id: "varchar"]                          |   "ten_id"            |   "some_pol_rls"
            "users"             |   "secondary" |     [user_uuid: "UUID"]                   |   "tenant"            |   "policy_prefix"
            "sys_users"         |   null        |     [id: "int", sys_uid: "varch"]         |   "customer"          |   "pol_rls"
            "sys_users"         |   "public"    |     [id: "int", sys_uid: "varch"]         |   "customer"          |   "pol_rls"
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
