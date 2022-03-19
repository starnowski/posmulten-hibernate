package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.*;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    private boolean initialized = false;

    private RLSPolicyEnricher rlsPolicyEnricher;
    private PosmultenUtilContext posmultenUtilContext;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        PersistentClassResolver persistentClassResolver = this.posmultenUtilContext.getPersistentClassResolver();
        PersistentClass persistentClass = persistentClassResolver.resolve(metadata, table);
        if (persistentClass == null) {
            return builder;
        }
        TenantTablePropertiesResolver tenantTablePropertiesResolver = posmultenUtilContext.getTenantTablePropertiesResolver();
        TenantTableProperties tenantTableProperties = tenantTablePropertiesResolver.resolve(persistentClass, table);
        if (tenantTableProperties != null) {
            NameGenerator nameGenerator = posmultenUtilContext.getNameGenerator();
            //TODO Pass schema and table name https://github.com/starnowski/posmulten/issues/239
            //TODO Logs
            System.out.println("RLS Table:" + table.getName() + " columns : " + tenantTableProperties.getPrimaryKeysColumnAndTypeMap());
            builder.createRLSPolicyForTable(tenantTableProperties.getTable(), tenantTableProperties.getPrimaryKeysColumnAndTypeMap(), tenantTableProperties.getTenantColumnName(), nameGenerator.generate("rls_policy_", table));
            builder.createTenantColumnForTable(tenantTableProperties.getTable());
        }
        return builder;
    }

    @Override
    public void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        this.posmultenUtilContext = serviceRegistryImplementor.getService(PosmultenUtilContext.class);
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    RLSPolicyEnricher getRlsPolicyEnricher() {
        return rlsPolicyEnricher;
    }

    void setRlsPolicyEnricher(RLSPolicyEnricher rlsPolicyEnricher) {
        this.rlsPolicyEnricher = rlsPolicyEnricher;
    }
}
