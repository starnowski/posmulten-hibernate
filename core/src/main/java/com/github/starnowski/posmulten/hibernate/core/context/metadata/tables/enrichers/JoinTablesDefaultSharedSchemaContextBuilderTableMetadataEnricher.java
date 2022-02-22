package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    private boolean initialized = false;
    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        Optional<Collection> pCollection = metadata.getCollectionBindings().stream().filter(collection -> table.equals(collection.getCollectionTable())).findFirst();
        Optional<PersistentClass> pClass = metadata.getEntityBindings().stream().filter(persistentClass -> table.equals(persistentClass.getTable())).findFirst();
        if (!pCollection.isPresent() || pClass.isPresent()) {
            return builder;
        }
        builder.createTenantColumnForTable(table.getName());
        String policyName = "rls_policy_" + table.getName();
        //TODO Pass schema and table name https://github.com/starnowski/posmulten/issues/239
        builder.createRLSPolicyForTable(table.getName(), new HashMap<>(), null, policyName);
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
}
