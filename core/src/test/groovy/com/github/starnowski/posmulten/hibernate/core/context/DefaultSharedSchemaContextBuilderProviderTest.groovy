package com.github.starnowski.posmulten.hibernate.core.context


import spock.lang.Specification
import spock.lang.Unroll

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
            map ||  schema  |   grantee |   currentTenantIdProperty
            new HashMap<>() ||  ""  |   ""  |   ""
    }

//    DefaultSharedSchemaContextBuilder defaultSharedSchemaContextBuilder = new DefaultSharedSchemaContextBuilder(null); // null schema --> public schema
//    defaultSharedSchemaContextBuilder.setGrantee("posmhib4sb-user"); // TODO move to configuration file
//    defaultSharedSchemaContextBuilder.setCurrentTenantIdProperty("posdemo.tenant"); // TODO move to configuration file
//    defaultSharedSchemaContextBuilder.setSetCurrentTenantIdFunctionName("set_pos_demo_tenant"); // TODO move to configuration file
//    defaultSharedSchemaContextBuilder.setCurrentTenantIdentifierAsDefaultValueForTenantColumnInAllTables(true); // TODO move to configuration file
//    defaultSharedSchemaContextBuilder.createValidTenantValueConstraint(Arrays.asList(INVALID_TENANT_ID, "tenants"), null, null); // TODO move to configuration file
}
