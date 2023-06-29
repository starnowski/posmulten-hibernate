package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers

import com.github.starnowski.posmulten.hibernate.common.context.metadata.tables.TenantTableProperties
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import com.github.starnowski.posmulten.postgresql.core.context.TableKey
import org.hibernate.boot.Metadata
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Table
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Unroll

class CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricherTest extends AbstractDefaultSharedSchemaContextBuilderTableMetadataEnricherTest<CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher> {

    CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher tested = new CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher()

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
    def "should enrich builder with function #functionName for table #tableName, schema #schema"(){
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
            tenantTableProperties.getSchema() >> schema
            tenantTablePropertiesResolver.resolve(persistentClass, table, metadata) >> tenantTableProperties
            def nameGenerator = Mock(NameGenerator)
            nameGenerator.generate("is_rls_record_exists_in_", table) >> functionName
            posmultenUtilContext.getNameGenerator() >> nameGenerator

            tested.init(null, serviceRegistryImplementor)

        when:
            def result = tested.enrich(builder, metadata, table)

        then:
            1 * builder.setNameForFunctionThatChecksIfRecordExistsInTable(new TableKey(tableName, schema), functionName)

        and: "returned the same object of builder"
            result.is(builder)

        where:
            tableName           |   schema          |   functionName
            "tab1"              |   null            |   "check_record_for"
            "users"             |   null            |   "does_record_exists"
            "sys_users"         |   null            |   "is_rec_ex"
            "tab1"              |   "public"        |   "check_record_for"
            "users"             |   "public"        |   "does_record_exists"
            "sys_users"         |   "public"        |   "is_rec_ex"
            "tab1"              |   "secondary"     |   "check_record_for"
            "users"             |   "third_schema"  |   "does_record_exists"
            "sys_users"         |   "some_sch"      |   "is_rec_ex"
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
