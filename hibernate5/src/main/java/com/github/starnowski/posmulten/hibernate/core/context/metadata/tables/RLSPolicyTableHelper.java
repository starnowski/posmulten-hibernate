package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import com.github.starnowski.posmulten.hibernate.common.context.metadata.tables.TenantTableProperties;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.TableKey;
import org.hibernate.mapping.Table;

public class RLSPolicyTableHelper {

    public void enrichBuilderWithTableRLSPolicy(DefaultSharedSchemaContextBuilder builder, Table table, TenantTableProperties tenantTableProperties, PosmultenUtilContext posmultenUtilContext) {
        if (tenantTableProperties != null) {
            NameGenerator nameGenerator = posmultenUtilContext.getNameGenerator();
            TableKey tableKey = new TableKey(tenantTableProperties.getTable(), tenantTableProperties.getSchema());
            builder.createRLSPolicyForTable(tableKey, tenantTableProperties.getPrimaryKeysColumnAndTypeMap(), tenantTableProperties.getTenantColumnName(), nameGenerator.generate("rls_policy_", table));
            TableUtils tableUtils = posmultenUtilContext.getTableUtils();
            String resolvedTenantColumn = builder.getSharedSchemaContextRequestCopy().resolveTenantColumnByTableKey(tableKey);
            if (!tableUtils.hasColumnWithName(table, resolvedTenantColumn)) {
                builder.createTenantColumnForTable(tableKey);
            }
        }
    }
}
