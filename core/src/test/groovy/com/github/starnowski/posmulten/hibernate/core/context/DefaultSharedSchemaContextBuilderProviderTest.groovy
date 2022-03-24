package com.github.starnowski.posmulten.hibernate.core.context


import spock.lang.Specification
import spock.lang.Unroll

import static com.github.starnowski.posmulten.hibernate.test.utils.MapBuilder.mapBuilder

class DefaultSharedSchemaContextBuilderProviderTest extends Specification {

    @Unroll
    def "should provide correct DefaultSharedSchemaContextBuilder based on configuration map #map, expected schema #schema, grantee #grantee, currentTenantIdProperty #currentTenantIdProperty"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProvider(map)

        when:
            def result = tested.get()

        then:
            def request = result.getSharedSchemaContextRequestCopy()
            request.defaultSchema == schema
            request.grantee == grantee
            request.currentTenantIdProperty == currentTenantIdProperty

        where:
            map             ||  schema |   grantee |   currentTenantIdProperty
            new HashMap<>() ||  null   |   null    |   "posmulten.tenant_id"
            mapBuilder().put("hibernate.default_schema", "public").build()        ||  "public"   |   null    |   "posmulten.tenant_id"
            mapBuilder().put("hibernate.posmulten.grantee", "some_user").build()        ||  null   |   "some_user"    |   "posmulten.tenant_id"
            mapBuilder().put("hibernate.posmulten.tenant.id.property", "pos.tenant").build()        ||  null   |   null    |   "pos.tenant"
            mapBuilder().put("hibernate.default_schema", "sch1").put("hibernate.posmulten.grantee", "owner").put("hibernate.posmulten.tenant.id.property", "p.ten").build()        ||  "sch1"   |   "owner"    |   "p.ten"
    }

    @Unroll
    def "should provide correct DefaultSharedSchemaContextBuilder based on configuration map #map, expected currentTenantIdentifierAsDefaultValueForTenantColumnInAllTables #currentTenantIdentifierAsDefaultValueForTenantColumnInAllTables, invalid tenants #invalidTenants"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProvider(map)

        when:
            def result = tested.get()

        then:
            def request = result.getSharedSchemaContextRequestCopy()
            request.currentTenantIdentifierAsDefaultValueForTenantColumnInAllTables == currentTenantIdentifierAsDefaultValueForTenantColumnInAllTables
            request.tenantValuesBlacklist == invalidTenants

        where:
            map             ||  currentTenantIdentifierAsDefaultValueForTenantColumnInAllTables |    invalidTenants
            new HashMap<>() ||  true   |   null
            mapBuilder().put("hibernate.posmulten.tenant.id.set.current.as.default", false).build() ||  false   |   null
            mapBuilder().put("hibernate.posmulten.tenant.id.set.current.as.default", true).build() ||  true   |   null
            mapBuilder().put("hibernate.posmulten.tenant.valid.values", "ten-1").build()        ||  true    |   ["ten-1"]
            mapBuilder().put("hibernate.posmulten.tenant.valid.values", "invalid,xxx").build()        ||  true    |   ["invalid", "xxx"]
            mapBuilder().put("hibernate.posmulten.tenant.id.set.current.as.default", false).put("hibernate.posmulten.tenant.valid.values", "invalid,xxx").build()        ||  false    |   ["invalid", "xxx"]
    }

    @Unroll
    def "should provide correct DefaultSharedSchemaContextBuilder based on configuration map #map, expected maximum identifier length #maxLength"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProvider(map)

        when:
            def result = tested.get()

        then:
            def request = result.getSharedSchemaContextRequestCopy()
            request.identifierMaxLength == maxLength

        where:
            map                                                                                         ||  maxLength
            new HashMap<>()                                                                             ||  com.github.starnowski.posmulten.hibernate.core.Properties.MAXIMUM_IDENTIFIER_LENGTH
            mapBuilder().put("hibernate.posmulten.maximum.identifier.length", "13").build()             ||  13
            mapBuilder().put("hibernate.posmulten.maximum.identifier.length", "176").build()            ||  176
    }
}
