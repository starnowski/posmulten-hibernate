package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;

import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.hibernate5.Properties.*;
import static java.util.Arrays.asList;

public class DefaultSharedSchemaContextBuilderProvider implements IDefaultSharedSchemaContextBuilderProvider {

    private final Map configuration;

    public DefaultSharedSchemaContextBuilderProvider(Map configuration) {
        this.configuration = configuration;
    }

    public DefaultSharedSchemaContextBuilder get() {
        DefaultSharedSchemaContextBuilder defaultSharedSchemaContextBuilder = configuration.containsKey("hibernate.default_schema") ? new DefaultSharedSchemaContextBuilder((String) configuration.get("hibernate.default_schema")) : new DefaultSharedSchemaContextBuilder(null);
        if (configuration.containsKey(GRANTEE)) {
            defaultSharedSchemaContextBuilder.setGrantee((String) configuration.get(GRANTEE));
        }
        if (configuration.containsKey(ID_PROPERTY)) {
            defaultSharedSchemaContextBuilder.setCurrentTenantIdProperty((String) configuration.get(ID_PROPERTY));
        }
        if (configuration.containsKey(TENANT_ID_SET_CURRENT_AS_DEFAULT_FLAG)) {
            defaultSharedSchemaContextBuilder.setCurrentTenantIdentifierAsDefaultValueForTenantColumnInAllTables((Boolean) configuration.get(TENANT_ID_SET_CURRENT_AS_DEFAULT_FLAG));
        } else {
            defaultSharedSchemaContextBuilder.setCurrentTenantIdentifierAsDefaultValueForTenantColumnInAllTables(true);
        }
        if (configuration.containsKey(TENANT_ID_INVALID_VALUES)) {
            defaultSharedSchemaContextBuilder.createValidTenantValueConstraint(asList(((String) configuration.get(TENANT_ID_INVALID_VALUES)).split(",")), null, null);
        }
        defaultSharedSchemaContextBuilder.setIdentifierMaxLength(configuration.containsKey(POSMULTEN_MAXIMUM_IDENTIFIER_LENGTH) ? Integer.parseInt((String) configuration.get(POSMULTEN_MAXIMUM_IDENTIFIER_LENGTH)) : MAXIMUM_IDENTIFIER_LENGTH);
        if (configuration.containsKey(GET_CURRENT_TENANT_FUNCTION_NAME)) {
            defaultSharedSchemaContextBuilder.setGetCurrentTenantIdFunctionName((String) configuration.get(GET_CURRENT_TENANT_FUNCTION_NAME));
        }
        if (configuration.containsKey(SET_CURRENT_TENANT_FUNCTION_NAME)) {
            defaultSharedSchemaContextBuilder.setSetCurrentTenantIdFunctionName((String) configuration.get(SET_CURRENT_TENANT_FUNCTION_NAME));
        }
        if (configuration.containsKey(EQUALS_CURRENT_TENANT_IDENTIFIER_FUNCTION_NAME)) {
            defaultSharedSchemaContextBuilder.setEqualsCurrentTenantIdentifierFunctionName((String) configuration.get(EQUALS_CURRENT_TENANT_IDENTIFIER_FUNCTION_NAME));
        }
        if (configuration.containsKey(TENANT_HAS_AUTHORITIES_FUNCTION_NAME)) {
            defaultSharedSchemaContextBuilder.setTenantHasAuthoritiesFunctionName((String) configuration.get(TENANT_HAS_AUTHORITIES_FUNCTION_NAME));
        }
        return defaultSharedSchemaContextBuilder;
    }
}
