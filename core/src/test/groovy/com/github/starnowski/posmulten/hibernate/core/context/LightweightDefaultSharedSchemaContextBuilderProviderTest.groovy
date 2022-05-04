package com.github.starnowski.posmulten.hibernate.core.context

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.starnowski.posmulten.hibernate.test.utils.MapBuilder.mapBuilder

class LightweightDefaultSharedSchemaContextBuilderProviderTest extends Specification {

    @Unroll
    def "should provide correct DefaultSharedSchemaContextBuilder based on configuration map #map, expected schema #schema, currentTenantIdProperty #currentTenantIdProperty"()
    {
        given:
            def tested = new LightweightDefaultSharedSchemaContextBuilderProvider(map)

        when:
            def result = tested.get()

        then:
            def request = result.getSharedSchemaContextRequestCopy()
            request.defaultSchema == schema
            request.currentTenantIdProperty == currentTenantIdProperty
            def context = result.build()
            context.getISetCurrentTenantIdFunctionPreparedStatementInvocationFactory() != null
            context.getIGetCurrentTenantIdFunctionInvocationFactory() != null
            context.getCurrentTenantIdPropertyType() != null

        where:
            map             ||  schema  |   currentTenantIdProperty
            new HashMap<>() ||  null   |   "posmulten.tenant_id"
            mapBuilder().put("hibernate.default_schema", "public").build()        ||  "public"   |   "posmulten.tenant_id"
            mapBuilder().put("hibernate.posmulten.grantee", "some_user").build()        ||  null   |   "posmulten.tenant_id"
            mapBuilder().put("hibernate.posmulten.tenant.id.property", "pos.tenant").build()        ||  null   |   "pos.tenant"
            mapBuilder().put("hibernate.default_schema", "sch1").put("hibernate.posmulten.grantee", "owner").put("hibernate.posmulten.tenant.id.property", "p.ten").build()        ||  "sch1"   |   "p.ten"
    }
}
