package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.ForeignKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper {

    public void enrichBuilder(DefaultSharedSchemaContextBuilder builder, ForeignKey foreignKey, NameGenerator nameGenerator) {
        List<Column> referenceColumns = foreignKey.getReferencedTable().getPrimaryKey().getColumns();
        List<Column> columns = foreignKey.getColumns();
        Map<String, String> foreignKeyToPrimaryKeyMap = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            foreignKeyToPrimaryKeyMap.put(columns.get(i).getName(), referenceColumns.get(i).getName());
        }
        //TODO Pass schema and table name https://github.com/starnowski/posmulten/issues/239
        builder.createSameTenantConstraintForForeignKey(foreignKey.getTable().getName(), foreignKey.getReferencedTable().getName(), foreignKeyToPrimaryKeyMap, nameGenerator.generate("rls_fk_con_", foreignKey.getTable(), columns));
    }
}
