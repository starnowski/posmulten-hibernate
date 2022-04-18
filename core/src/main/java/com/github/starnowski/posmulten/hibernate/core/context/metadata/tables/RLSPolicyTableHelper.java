package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.TableKey;
import org.hibernate.mapping.Table;

public class RLSPolicyTableHelper {

    public void enrichBuilderWithTableRLSPolicy(DefaultSharedSchemaContextBuilder builder, Table table, TenantTableProperties tenantTableProperties, PosmultenUtilContext posmultenUtilContext)
    {
        if (tenantTableProperties != null) {
            NameGenerator nameGenerator = posmultenUtilContext.getNameGenerator();
            //TODO Pass schema and table name https://github.com/starnowski/posmulten/issues/239
            builder.createRLSPolicyForTable(tenantTableProperties.getTable(), tenantTableProperties.getPrimaryKeysColumnAndTypeMap(), tenantTableProperties.getTenantColumnName(), nameGenerator.generate("rls_policy_", table));
            TableUtils tableUtils = posmultenUtilContext.getTableUtils();
            String resolvedTenantColumn = builder.getSharedSchemaContextRequestCopy().resolveTenantColumnByTableKey(new TableKey(tenantTableProperties.getTable(), tenantTableProperties.getSchema()));
            if (!tableUtils.hasColumnWithName(table, resolvedTenantColumn)) {
                //TODO Pass schema and table name https://github.com/starnowski/posmulten/issues/239
                builder.createTenantColumnForTable(tenantTableProperties.getTable());
            }
        }
    }
}
