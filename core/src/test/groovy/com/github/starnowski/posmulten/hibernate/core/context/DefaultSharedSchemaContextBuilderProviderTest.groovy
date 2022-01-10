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

//    DefaultSharedSchemaContextBuilder defaultSharedSchemaContextBuilder = new DefaultSharedSchemaContextBuilder(null); // null schema --> public schema
//    defaultSharedSchemaContextBuilder.setGrantee("posmhib4sb-user"); // TODO move to configuration file
//    defaultSharedSchemaContextBuilder.setCurrentTenantIdProperty("posdemo.tenant"); // TODO move to configuration file
//    defaultSharedSchemaContextBuilder.setSetCurrentTenantIdFunctionName("set_pos_demo_tenant"); // TODO move to configuration file
//    defaultSharedSchemaContextBuilder.setCurrentTenantIdentifierAsDefaultValueForTenantColumnInAllTables(true); // TODO move to configuration file
//    defaultSharedSchemaContextBuilder.createValidTenantValueConstraint(Arrays.asList(INVALID_TENANT_ID, "tenants"), null, null); // TODO move to configuration file
}
