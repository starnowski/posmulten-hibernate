package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables;

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.TableKey;
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
        builder.createSameTenantConstraintForForeignKey(new TableKey(foreignKey.getTable().getName(), foreignKey.getTable().getSchema()), new TableKey(foreignKey.getReferencedTable().getName(), foreignKey.getReferencedTable().getSchema()), foreignKeyToPrimaryKeyMap, nameGenerator.generate("rls_fk_con_", foreignKey.getTable(), columns));
    }
}
