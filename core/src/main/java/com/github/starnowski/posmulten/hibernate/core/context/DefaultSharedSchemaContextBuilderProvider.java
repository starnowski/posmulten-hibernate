package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;

import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.core.Properties.*;
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
        if (configuration.containsKey(TENANT_ID_VALID_VALUES)) {
            defaultSharedSchemaContextBuilder.createValidTenantValueConstraint(asList(((String) configuration.get(TENANT_ID_VALID_VALUES)).split(",")), null, null);
        }
        defaultSharedSchemaContextBuilder.setIdentifierMaxLength(configuration.containsKey(POSMULTEN_MAXIMUM_IDENTIFIER_LENGTH) ? Integer.parseInt((String) configuration.get(POSMULTEN_MAXIMUM_IDENTIFIER_LENGTH)) : MAXIMUM_IDENTIFIER_LENGTH);
        return defaultSharedSchemaContextBuilder;
    }
}
