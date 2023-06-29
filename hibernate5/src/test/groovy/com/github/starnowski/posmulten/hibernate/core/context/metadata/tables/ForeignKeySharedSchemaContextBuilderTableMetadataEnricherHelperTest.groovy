package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import com.github.starnowski.posmulten.postgresql.core.context.TableKey
import org.hibernate.mapping.Column
import org.hibernate.mapping.ForeignKey
import org.hibernate.mapping.PrimaryKey
import org.hibernate.mapping.Table
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.stream.Collectors.toList

class ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelperTest extends Specification {

    @Unroll
    def "should enrich builder with foreign key for table #tableName in schema #schema and columns #expectedColumnsReference, reference table #referenceTableName in schema #referenceTableSchemaName, expected constraint name #cn, table columns #tableColumns, reference table columns #referenceTableColumns"() {
        given:
            def tested = new ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper()
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def foreignKey = Mock(ForeignKey)
            def nameGenerator = Mock(NameGenerator)
            def referenceTable = Mock(Table)
            def table = Mock(Table)
            def primaryKey = Mock(PrimaryKey)
            def foreignKeyColumns = mapColumns(tableColumns)
            foreignKey.getReferencedTable() >> referenceTable
            foreignKey.getTable() >> table
            referenceTable.getPrimaryKey() >> primaryKey
            referenceTable.getName() >> referenceTableName
            referenceTable.getSchema() >> referenceTableSchemaName
            table.getName() >> tableName
            table.getSchema() >> schema
            primaryKey.getColumns() >> mapColumns(referenceTableColumns)
            foreignKey.getColumns() >> foreignKeyColumns

        when:
            tested.enrichBuilder(builder, foreignKey, nameGenerator)

        then:
            1 * nameGenerator.generate("rls_fk_con_", table, foreignKeyColumns) >> cn
            1 * builder.createSameTenantConstraintForForeignKey(new TableKey(tableName, schema), new TableKey(referenceTableName, referenceTableSchemaName), expectedColumnsReference, cn)

        where:
            tableName       |   schema          |   referenceTableName      | referenceTableSchemaName  |   cn                  |   tableColumns                    |   referenceTableColumns   ||  expectedColumnsReference
            "posts"         |   null            |   "users"                 |  null                     |   "some_con"          |   ["user_id"]                     |   ["id"]                  ||  [user_id: "id"]
            "comments"      |   null            |   "posts"                 |  null                     |   "comm_fk_axczv"     |   ["post_id", "post_user_id"]     |   ["id", "user_id"]       ||  [post_id: "id", post_user_id: "user_id"]
            "posts"         |   "secondary"     |   "users"                 |  null                     |   "some_con"          |   ["user_id"]                     |   ["id"]                  ||  [user_id: "id"]
            "comments"      |   "secondary"     |   "posts"                 |  null                     |   "comm_fk_axczv"     |   ["post_id", "post_user_id"]     |   ["id", "user_id"]       ||  [post_id: "id", post_user_id: "user_id"]
            "posts"         |   "secondary"     |   "users"                 |  "pos_sch"                |   "some_con"          |   ["user_id"]                     |   ["id"]                  ||  [user_id: "id"]
            "comments"      |   "secondary"     |   "posts"                 |  "pos_sch"                |   "comm_fk_axczv"     |   ["post_id", "post_user_id"]     |   ["id", "user_id"]       ||  [post_id: "id", post_user_id: "user_id"]
            "posts"         |   null            |   "users"                 |  "pos_sch"                |   "some_con"          |   ["user_id"]                     |   ["id"]                  ||  [user_id: "id"]
            "comments"      |   null            |   "posts"                 |  "pos_sch"                |   "comm_fk_axczv"     |   ["post_id", "post_user_id"]     |   ["id", "user_id"]       ||  [post_id: "id", post_user_id: "user_id"]
    }

    private List<Column> mapColumns(List<String> columnsNames){
        return columnsNames.stream().map({
            Column column = Mock(Column)
            column.getName() >> it
            return column
        }).collect(toList())
    }
}
