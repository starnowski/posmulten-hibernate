package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables

import com.github.starnowski.posmulten.hibernate.common.context.metadata.tables.TenantTableProperties
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.PosmultenUtilContext
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import com.github.starnowski.posmulten.postgresql.core.context.SharedSchemaContextRequest
import com.github.starnowski.posmulten.postgresql.core.context.TableKey
import org.hibernate.mapping.Table
import spock.lang.Specification
import spock.lang.Unroll

class RLSPolicyTableHelperTest extends Specification {

    RLSPolicyTableHelper tested = new RLSPolicyTableHelper()

    @Unroll
    def "should enrich builder with rls policy #policyName for table #tableName, schema #schema, tenant column #tenantColumnName and pass primary key columns #primaryKeysColumnAndTypeMap"(){
        given:
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def table = Mock(Table)
            def tenantTablePropertiesResolver = Mock(TenantTablePropertiesResolver)
            posmultenUtilContext.getTenantTablePropertiesResolver() >> tenantTablePropertiesResolver
            def tenantTableProperties = Mock(TenantTableProperties)
            tenantTableProperties.getTable() >> tableName
            tenantTableProperties.getPrimaryKeysColumnAndTypeMap() >> primaryKeysColumnAndTypeMap
            tenantTableProperties.getTenantColumnName() >> tenantColumnName
            tenantTableProperties.getSchema() >> schema
            def nameGenerator = Mock(NameGenerator)
            nameGenerator.generate("rls_policy_", table) >> policyName
            posmultenUtilContext.getNameGenerator() >> nameGenerator

            def requestCopy = Mock(SharedSchemaContextRequest)
            builder.getSharedSchemaContextRequestCopy() >> requestCopy
            requestCopy.resolveTenantColumnByTableKey(new TableKey(tableName, schema)) >> tenantColumnName

            def tableUtils = Mock(TableUtils)
            posmultenUtilContext.getTableUtils() >> tableUtils
            tableUtils.hasColumnWithName(table, tenantColumnName) >> false

        when:
            tested.enrichBuilderWithTableRLSPolicy(builder, table, tenantTableProperties, posmultenUtilContext)

        then:
            1 * builder.createRLSPolicyForTable(new TableKey(tableName, schema), primaryKeysColumnAndTypeMap, tenantColumnName, policyName)

        and: "create tenant column when column does not yet exist for table"
            1 * builder.createTenantColumnForTable(new TableKey(tableName, schema))

        where:
            tableName           |   schema      |   primaryKeysColumnAndTypeMap             |   tenantColumnName    |   policyName
            "tab1"              |   null        |  [id: "varchar"]                          |   "ten_id"            |   "some_pol_rls"
            "users"             |   "secondary" |     [user_uuid: "UUID"]                   |   "tenant"            |   "policy_prefix"
            "sys_users"         |   null        |     [id: "int", sys_uid: "varch"]         |   "customer"          |   "pol_rls"
            "sys_users"         |   "public"    |     [id: "int", sys_uid: "varch"]         |   "customer"          |   "pol_rls"
    }

    @Unroll
    def "should enrich builder with rls policy #policyName for table #tableName, schema #schema, with resolved tenant column #resolvedTenantColumnName"(){
        given:
            def primaryKeysColumnAndTypeMap = [id: "int", sys_uid: "varch"]
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def table = Mock(Table)
            def tenantTablePropertiesResolver = Mock(TenantTablePropertiesResolver)
            posmultenUtilContext.getTenantTablePropertiesResolver() >> tenantTablePropertiesResolver
            def tenantTableProperties = Mock(TenantTableProperties)
            tenantTableProperties.getTable() >> tableName
            tenantTableProperties.getPrimaryKeysColumnAndTypeMap() >> primaryKeysColumnAndTypeMap
            tenantTableProperties.getTenantColumnName() >> null
            tenantTableProperties.getSchema() >> schema
            def nameGenerator = Mock(NameGenerator)
            nameGenerator.generate("rls_policy_", table) >> policyName
            posmultenUtilContext.getNameGenerator() >> nameGenerator

            def requestCopy = Mock(SharedSchemaContextRequest)
            builder.getSharedSchemaContextRequestCopy() >> requestCopy
            requestCopy.resolveTenantColumnByTableKey(new TableKey(tableName, schema)) >> resolvedTenantColumnName

            def tableUtils = Mock(TableUtils)
            posmultenUtilContext.getTableUtils() >> tableUtils
            tableUtils.hasColumnWithName(table, resolvedTenantColumnName) >> false

        when:
            tested.enrichBuilderWithTableRLSPolicy(builder, table, tenantTableProperties, posmultenUtilContext)

        then:
            1 * builder.createRLSPolicyForTable(new TableKey(tableName, schema), primaryKeysColumnAndTypeMap, null, policyName)

        and: "create tenant column when column does not yet exist for table"
            1 * builder.createTenantColumnForTable(new TableKey(tableName, schema))

        where:
            tableName           |   schema      |   resolvedTenantColumnName    |   policyName
            "tab1"              |   null        |  "ten_id"                     |   "some_pol_rls"
            "users"             |   "secondary" |  "tenant"                     |   "policy_prefix"
            "sys_users"         |   null        |  "customer"                   |   "pol_rls"
            "sys_users"         |   "public"    |  "customer"                   |   "pol_rls"
    }

    @Unroll
    def "should enrich builder with rls policy #policyName for table #tableName, schema #schema, with resolved tenant column #resolvedTenantColumnName that already exist for table"(){
        given:
            def primaryKeysColumnAndTypeMap = [id: "int", sys_uid: "varch"]
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def table = Mock(Table)
            def tenantTablePropertiesResolver = Mock(TenantTablePropertiesResolver)
            posmultenUtilContext.getTenantTablePropertiesResolver() >> tenantTablePropertiesResolver
            def tenantTableProperties = Mock(TenantTableProperties)
            tenantTableProperties.getTable() >> tableName
            tenantTableProperties.getPrimaryKeysColumnAndTypeMap() >> primaryKeysColumnAndTypeMap
            tenantTableProperties.getTenantColumnName() >> null
            tenantTableProperties.getSchema() >> schema
            def nameGenerator = Mock(NameGenerator)
            nameGenerator.generate("rls_policy_", table) >> policyName
            posmultenUtilContext.getNameGenerator() >> nameGenerator

            def requestCopy = Mock(SharedSchemaContextRequest)
            builder.getSharedSchemaContextRequestCopy() >> requestCopy
            requestCopy.resolveTenantColumnByTableKey(new TableKey(tableName, schema)) >> resolvedTenantColumnName

            def tableUtils = Mock(TableUtils)
            posmultenUtilContext.getTableUtils() >> tableUtils
            tableUtils.hasColumnWithName(table, resolvedTenantColumnName) >> true

        when:
            tested.enrichBuilderWithTableRLSPolicy(builder, table, tenantTableProperties, posmultenUtilContext)

        then:
            1 * builder.createRLSPolicyForTable(new TableKey(tableName, schema), primaryKeysColumnAndTypeMap, null, policyName)

        and: "create tenant column when column does not yet exist for table"
            0 * builder.createTenantColumnForTable(_)

        where:
            tableName           |   schema      |   resolvedTenantColumnName    |   policyName
            "tab1"              |   null        |  "ten_id"                     |   "some_pol_rls"
            "users"             |   "secondary" |  "tenant"                     |   "policy_prefix"
            "sys_users"         |   null        |  "customer"                   |   "pol_rls"
            "sys_users"         |   "public"    |  "customer"                   |   "pol_rls"
    }

    def "should not enrich builder when table properties can not be resolve for table"(){
        given:
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def table = Mock(Table)
            def nameGenerator = Mock(NameGenerator)
            posmultenUtilContext.getNameGenerator() >> nameGenerator

            def tableUtils = Mock(TableUtils)
            posmultenUtilContext.getTableUtils() >> tableUtils

        when:
            tested.enrichBuilderWithTableRLSPolicy(builder, table, null, posmultenUtilContext)

        then:
            0 * builder._
    }
}
