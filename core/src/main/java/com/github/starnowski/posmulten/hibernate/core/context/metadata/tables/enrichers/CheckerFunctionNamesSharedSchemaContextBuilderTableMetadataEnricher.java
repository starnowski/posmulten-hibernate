package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTableProperties;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;
import java.util.Optional;

public class CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    private boolean initialized = false;

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
        TenantTablePropertiesResolver tenantTablePropertiesResolver = new TenantTablePropertiesResolver();
        TenantTableProperties tenantTableProperties = tenantTablePropertiesResolver.resolve(persistentClass, table);
        if (tenantTableProperties != null) {
            String functionName = "is_rls_record_exists_in_" + tenantTableProperties.getTable();
            builder.setNameForFunctionThatChecksIfRecordExistsInTable(tenantTableProperties.getTable(), functionName);
        }
        return builder;
    }

    @Override
    public void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
