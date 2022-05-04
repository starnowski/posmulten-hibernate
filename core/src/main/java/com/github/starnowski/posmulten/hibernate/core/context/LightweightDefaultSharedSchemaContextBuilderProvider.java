package com.github.starnowski.posmulten.hibernate.core.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;

import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.core.Properties.ID_PROPERTY;

public class LightweightDefaultSharedSchemaContextBuilderProvider implements IDefaultSharedSchemaContextBuilderProvider {

    private final Map configuration;

    public LightweightDefaultSharedSchemaContextBuilderProvider(Map configuration) {
        this.configuration = configuration;
    }
    @Override
    public DefaultSharedSchemaContextBuilder get() {
        DefaultSharedSchemaContextBuilder defaultSharedSchemaContextBuilder = configuration.containsKey("hibernate.default_schema") ? new DefaultSharedSchemaContextBuilder((String) configuration.get("hibernate.default_schema")) : new DefaultSharedSchemaContextBuilder(null);
        if (configuration.containsKey(ID_PROPERTY)) {
            defaultSharedSchemaContextBuilder.setCurrentTenantIdProperty((String) configuration.get(ID_PROPERTY));
        }
        return defaultSharedSchemaContextBuilder;
    }
}
