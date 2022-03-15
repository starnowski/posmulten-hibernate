package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.RLSPolicyEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTableProperties;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;
import java.util.Optional;

public class RLSPolicyDefaultSharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    private boolean initialized = false;

    private RLSPolicyEnricher rlsPolicyEnricher;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        if (!table.isPhysicalTable()) {
            return builder;
        }
        Optional<PersistentClass> pClass = metadata.getEntityBindings().stream().filter(persistentClass -> table.equals(persistentClass.getTable())).findFirst();
        if (!pClass.isPresent()) {
            return builder;
        }
        PersistentClass persistentClass = pClass.get();
        //TODO RLSUtilContext
        TenantTablePropertiesResolver tenantTablePropertiesResolver = new TenantTablePropertiesResolver();
        TenantTableProperties tenantTableProperties = tenantTablePropertiesResolver.resolve(persistentClass, table);
        if (tenantTableProperties != null) {
            String policyName = "rls_policy_" + tenantTableProperties.getTable();
            //TODO Pass schema and table name https://github.com/starnowski/posmulten/issues/239
            builder.createRLSPolicyForTable(tenantTableProperties.getTable(), tenantTableProperties.getPrimaryKeysColumnAndTypeMap(), tenantTableProperties.getTenantColumnName(), policyName);
            builder.createTenantColumnForTable(tenantTableProperties.getTable());
        }
        return builder;
    }

    @Override
    public void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
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
