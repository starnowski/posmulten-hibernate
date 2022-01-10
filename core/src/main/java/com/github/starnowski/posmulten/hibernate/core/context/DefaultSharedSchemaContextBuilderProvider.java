package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;

import java.util.Map;

public class DefaultSharedSchemaContextBuilderProvider implements IDefaultSharedSchemaContextBuilderProvider {

    private final Map configuration;

    public DefaultSharedSchemaContextBuilderProvider(Map configuration) {
        this.configuration = configuration;
    }

    public DefaultSharedSchemaContextBuilder get() {
        DefaultSharedSchemaContextBuilder defaultSharedSchemaContextBuilder = configuration.containsKey("hibernate.default_schema") ? new DefaultSharedSchemaContextBuilder((String) configuration.get("hibernate.default_schema")) : new DefaultSharedSchemaContextBuilder(null);
        if (configuration.containsKey("hibernate.posmulten.grantee")) {
            defaultSharedSchemaContextBuilder.setGrantee((String) configuration.get("hibernate.posmulten.grantee"));
        }
        if (configuration.containsKey("hibernate.posmulten.tenant.id.property")) {
            defaultSharedSchemaContextBuilder.setCurrentTenantIdProperty((String) configuration.get("hibernate.posmulten.tenant.id.property"));
        }
        return defaultSharedSchemaContextBuilder;
    }
}