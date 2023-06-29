package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.enrichers.CurrentTenantIdPropertyTypeEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.enrichers.GetCurrentTenantIdFunctionDefinitionEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.enrichers.SetCurrentTenantIdFunctionDefinitionEnricher;

import java.util.Arrays;
import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.hibernate5.Properties.*;

public class LightweightDefaultSharedSchemaContextBuilderProvider implements IDefaultSharedSchemaContextBuilderProvider {

    private final Map configuration;

    public LightweightDefaultSharedSchemaContextBuilderProvider(Map configuration) {
        this.configuration = configuration;
    }

    @Override
    public DefaultSharedSchemaContextBuilder get() {
        DefaultSharedSchemaContextBuilder defaultSharedSchemaContextBuilder = configuration.containsKey("hibernate.default_schema") ? new DefaultSharedSchemaContextBuilder((String) configuration.get("hibernate.default_schema")) : new DefaultSharedSchemaContextBuilder(null);
        defaultSharedSchemaContextBuilder.setEnrichers(Arrays.asList(new GetCurrentTenantIdFunctionDefinitionEnricher(), new SetCurrentTenantIdFunctionDefinitionEnricher(), new CurrentTenantIdPropertyTypeEnricher()));
        if (configuration.containsKey(ID_PROPERTY)) {
            defaultSharedSchemaContextBuilder.setCurrentTenantIdProperty((String) configuration.get(ID_PROPERTY));
        }
        if (configuration.containsKey(GET_CURRENT_TENANT_FUNCTION_NAME)) {
            defaultSharedSchemaContextBuilder.setGetCurrentTenantIdFunctionName((String) configuration.get(GET_CURRENT_TENANT_FUNCTION_NAME));
        }
        if (configuration.containsKey(SET_CURRENT_TENANT_FUNCTION_NAME)) {
            defaultSharedSchemaContextBuilder.setSetCurrentTenantIdFunctionName((String) configuration.get(SET_CURRENT_TENANT_FUNCTION_NAME));
        }
        return defaultSharedSchemaContextBuilder;
    }
}
