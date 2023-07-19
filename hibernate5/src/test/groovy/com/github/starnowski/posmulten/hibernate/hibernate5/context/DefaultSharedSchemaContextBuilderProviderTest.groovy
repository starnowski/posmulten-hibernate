package com.github.starnowski.posmulten.hibernate.hibernate5.context


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
            mapBuilder().put("hibernate.posmulten.tenant.id.values.blacklist", "ten-1").build()        ||  true    |   ["ten-1"]
            mapBuilder().put("hibernate.posmulten.tenant.id.values.blacklist", "invalid,xxx").build()        ||  true    |   ["invalid", "xxx"]
            mapBuilder().put("hibernate.posmulten.tenant.id.set.current.as.default", false).put("hibernate.posmulten.tenant.id.values.blacklist", "invalid,xxx").build()        ||  false    |   ["invalid", "xxx"]
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
            new HashMap<>()                                                                             ||  com.github.starnowski.posmulten.hibernate.hibernate5.Properties.MAXIMUM_IDENTIFIER_LENGTH
            mapBuilder().put("hibernate.posmulten.maximum.identifier.length", "13").build()             ||  13
            mapBuilder().put("hibernate.posmulten.maximum.identifier.length", "176").build()            ||  176
    }

    @Unroll
    def "should set name for function that sets current tenant based on property 'hibernate.posmulten.function.setcurrenttenant.name', expected #value"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProvider(map)

        when:
            def result = tested.get()

        then:
            result.getSharedSchemaContextRequestCopy().getSetCurrentTenantIdFunctionName() == value

        where:
            map                                                                                                     ||  value
            mapBuilder().put("hibernate.posmulten.function.setcurrenttenant.name", "set_tenant").build()            ||  "set_tenant"
            mapBuilder().put("hibernate.posmulten.function.setcurrenttenant.name", "_tenant_should_be").build()     ||  "_tenant_should_be"
    }

    @Unroll
    def "should set name for function that gets current tenant based on property 'hibernate.posmulten.function.getcurrenttenant.name', expected #value"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProvider(map)

        when:
            def result = tested.get()

        then:
            result.getSharedSchemaContextRequestCopy().getGetCurrentTenantIdFunctionName() == value

        where:
        map                                                                                                     ||  value
            mapBuilder().put("hibernate.posmulten.function.getcurrenttenant.name", "get_tenant").build()        ||  "get_tenant"
            mapBuilder().put("hibernate.posmulten.function.getcurrenttenant.name", "_tenant_value_is").build()  ||  "_tenant_value_is"
    }

    @Unroll
    def "should set name for function that checks if passed identifier equals to current tenant based on property 'hibernate.posmulten.function.equalscurrenttenantidentifier.name', expected #value"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProvider(map)

        when:
            def result = tested.get()

        then:
            result.getSharedSchemaContextRequestCopy().getEqualsCurrentTenantIdentifierFunctionName() == value

        where:
            map                                                                                                             ||  value
            mapBuilder().put("hibernate.posmulten.function.equalscurrenttenantidentifier.name", "equals_tenant").build()    ||  "equals_tenant"
            mapBuilder().put("hibernate.posmulten.function.equalscurrenttenantidentifier.name", "_is_equal").build()        ||  "_is_equal"
    }

    @Unroll
    def "should set name for function that checks if tenant has authorities based on property 'hibernate.posmulten.function.tenanthasauthorities.name', expected #value"()
    {
        given:
            def tested = new DefaultSharedSchemaContextBuilderProvider(map)

        when:
            def result = tested.get()

        then:
            result.getSharedSchemaContextRequestCopy().getTenantHasAuthoritiesFunctionName() == value

        where:
            map                                                                                                                 ||  value
            mapBuilder().put("hibernate.posmulten.function.tenanthasauthorities.name", "ten_has_aut").build()                   ||  "ten_has_aut"
            mapBuilder().put("hibernate.posmulten.function.tenanthasauthorities.name", "tenanthasauthorities_fun").build()      ||  "tenanthasauthorities_fun"
    }
}
