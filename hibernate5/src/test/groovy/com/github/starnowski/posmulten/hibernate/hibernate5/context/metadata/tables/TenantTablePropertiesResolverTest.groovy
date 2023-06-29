package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables

import com.github.starnowski.posmulten.hibernate.common.TenantTable
import org.hibernate.boot.Metadata
import org.hibernate.boot.model.relational.Database
import org.hibernate.dialect.Dialect
import org.hibernate.mapping.Column
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.PrimaryKey
import org.hibernate.mapping.Table
import spock.lang.Specification
import spock.lang.Unroll

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class TenantTablePropertiesResolverTest extends Specification {

    def tested = new TenantTablePropertiesResolver()

    @Unroll
    def "should return null if mapped type does not have correct annotation" ()
    {
        given:
            def persistentClass = Mock(PersistentClass)
            persistentClass.getMappedClass() >> TableWithoutTenantTableAnnotation.class

        when:
            def result = tested.resolve(persistentClass, table, null)

        then:
            result == null

        where:
            table << [null, prepareTable("posts", null), prepareTable("comment", [:]), prepareTable("user", ["id": "numeric"])]
    }

    @Unroll
    def "should return null if type does not have correct annotation" ()
    {
        when:
        def result = tested.resolve(TableWithoutTenantTableAnnotation.class, table, null)

        then:
        result == null

        where:
        table << [null, prepareTable("posts", null), prepareTable("comment", [:]), prepareTable("user", ["id": "numeric"])]
    }

    @Unroll
    def "should return object with tenant column, class: #clazz, expected column: #expectedTenantColumn" ()
    {
        given:
            def persistentClass = Mock(PersistentClass)
            persistentClass.getMappedClass() >> clazz

        when:
            def result = tested.resolve(persistentClass, prepareTable("user", ["id": "numeric"]),null)

        then:
            result.getTenantColumnName() == expectedTenantColumn

        where:
            clazz                                                   ||  expectedTenantColumn
            TableWithDefaultTenantTableAnnotation                   ||  null
            TableWithTenantTableAnnotationAndEmptyTenantColumn      ||  null
            TableWithTenantTableAnnotationAndSpecifiedTenantColumn  ||  "ten_col_id"
    }

    @Unroll
    def "should return object with table: #expectedTable, schema #expectedSchema expected columns for primary keys: #expectedColumns" ()
    {
        given:
            def persistentClass = Mock(PersistentClass)
            persistentClass.getMappedClass() >> TableWithDefaultTenantTableAnnotation.class

        when:
            def result = tested.resolve(persistentClass, table, null)

        then:
            result.getTable() == expectedTable
            result.getSchema() == expectedSchema
            result.getPrimaryKeysColumnAndTypeMap() == expectedColumns

        where:
            table                                                                                   ||  expectedTable   |   expectedSchema  |  expectedColumns
            prepareTable("user", ["id": "numeric"])                                                 ||  "user"    | null    |  ["id": "numeric"]
            prepareTable("comments", ["comment_id": "long"])                                        ||  "comments"  | null    |   ["comment_id": "long"]
            prepareTable("posts", ["user_id": "numeric", "post_uuid" : "UUID"])                     ||  "posts"   | null    |   ["user_id": "numeric", "post_uuid" : "UUID"]
            prepareTable("user", "non_public_schema", ["id": "numeric"])                            ||  "user"    | "non_public_schema"    |  ["id": "numeric"]
            prepareTable("comments", "public", ["comment_id": "long"])                              ||  "comments"  | "public"    |   ["comment_id": "long"]
            prepareTable("posts", "secondary_sh",  ["user_id": "numeric", "post_uuid" : "UUID"])    ||  "posts"   | "secondary_sh"    |   ["user_id": "numeric", "post_uuid" : "UUID"]
    }

    @Unroll
    def "should return object with table: #expectedTable, schema #expectedSchema without primary keys map when table does not have primary keys" ()
    {
        given:
            def persistentClass = Mock(PersistentClass)
            persistentClass.getMappedClass() >> TableWithDefaultTenantTableAnnotation.class

        when:
            def result = tested.resolve(persistentClass, table, null)

        then:
            result.getTable() == expectedTable
            result.getSchema() == expectedSchema

        and: "empty primary key columns map has to be empty"
            result.getPrimaryKeysColumnAndTypeMap().isEmpty()

        where:
            table                                                           ||  expectedTable   |   expectedSchema
            prepareTableWithoutPrimaryKey("user", "non_public_schema")      ||  "user"          | "non_public_schema"
            prepareTableWithoutPrimaryKey("comments", "public")             ||  "comments"      | "public"
            prepareTableWithoutPrimaryKey("posts", "secondary_sh")          ||  "posts"         | "secondary_sh"
    }

    @Unroll
    def "should return object with table: #expectedTable, schema #expectedSchema expected columns for primary keys: #expectedColumns for class object" ()
    {
        when:
            def result = tested.resolve(TableWithDefaultTenantTableAnnotation.class, table, null)

        then:
            result.getTable() == expectedTable
            result.getSchema() == expectedSchema
            result.getPrimaryKeysColumnAndTypeMap() == expectedColumns

        where:
            table                                                                                   ||  expectedTable   |   expectedSchema  |  expectedColumns
            prepareTable("user", ["id": "numeric"])                                                 ||  "user"    | null    |  ["id": "numeric"]
            prepareTable("comments", ["comment_id": "long"])                                        ||  "comments"  | null    |   ["comment_id": "long"]
            prepareTable("posts", ["user_id": "numeric", "post_uuid" : "UUID"])                     ||  "posts"   | null    |   ["user_id": "numeric", "post_uuid" : "UUID"]
            prepareTable("user", "non_public_schema", ["id": "numeric"])                            ||  "user"    | "non_public_schema"    |  ["id": "numeric"]
            prepareTable("comments", "public", ["comment_id": "long"])                              ||  "comments"  | "public"    |   ["comment_id": "long"]
            prepareTable("posts", "secondary_sh",  ["user_id": "numeric", "post_uuid" : "UUID"])    ||  "posts"   | "secondary_sh"    |   ["user_id": "numeric", "post_uuid" : "UUID"]
    }

    @Unroll
    def "should return object with table: #expectedTable and set correct column types based on metadata #sqlTypesBasedOnMetadata expected columns for primary keys: #expectedColumns" ()
    {
        given:
            def persistentClass = Mock(PersistentClass)
            persistentClass.getMappedClass() >> TableWithDefaultTenantTableAnnotation.class
            Metadata metadata = Mock(Metadata)
            Database database = Mock(Database)
            Dialect dialect = Mock(Dialect)
            metadata.getDatabase() >> database
            database.getDialect() >> dialect
            sqlTypesBasedOnMetadata.entrySet().forEach({it.getKey()
                def iterator = table.getPrimaryKey().getColumnIterator()
                while (iterator.hasNext()) {
                    def column = iterator.next()
                    if (it.getKey() == column.getName()) {
                        when(column.getSqlType(dialect, metadata)).thenReturn(it.getValue())
                    }
                }
            })

        when:
            def result = tested.resolve(persistentClass, table, metadata)

        then:
            result.getTable() == expectedTable
            result.getPrimaryKeysColumnAndTypeMap() == expectedColumns

        where:
            table                                                               |  sqlTypesBasedOnMetadata      ||  expectedTable   |  expectedColumns
            prepareTable("user", ["id": null])                                  | ["id": "int7"]                ||  "user"    |   ["id": "int7"]
            prepareTable("comments", ["comment_id": null])                      | ["comment_id": "bigint"]      ||  "comments"  |   ["comment_id": "bigint"]
            prepareTable("posts", ["user_id": null, "post_uuid" : "UUID"])      | ["user_id": "int2"]           ||  "posts"   |   ["user_id": "int2", "post_uuid" : "UUID"]
    }

    private static Table prepareTable(String name, Map<String, String> primaryColumns)
    {
        return prepareTable(name, null, primaryColumns)
    }

    private static Table prepareTable(String name, String schema, Map<String, String> primaryColumns)
    {
        def table = mock(Table.class)
        def primaryKey = mock(PrimaryKey.class)
        List<Column> columns = new ArrayList<>()
        if (primaryColumns != null) {
            primaryColumns.forEach({key, value ->
                def column = mock(Column.class)
                when(column.getName()).thenReturn(key)
                when(column.getSqlType()).thenReturn(value)
                columns.add(column)
            })
        }
        when(table.getPrimaryKey()).thenReturn(primaryKey)
        when(table.getName()).thenReturn(name)
        when(table.getSchema()).thenReturn(schema)
        when(primaryKey.getColumnIterator()).thenAnswer({columns.iterator()} )
        return table
    }

    private static Table prepareTableWithoutPrimaryKey(String name, String schema)
    {
        def table = mock(Table.class)
        List<Column> columns = new ArrayList<>()
        when(table.getPrimaryKey()).thenReturn(null)
        when(table.getName()).thenReturn(name)
        when(table.getSchema()).thenReturn(schema)
        return table
    }

    private static class TableWithoutTenantTableAnnotation {}

    @TenantTable
    private static class TableWithDefaultTenantTableAnnotation {}

    @TenantTable(tenantIdColumn = "    ")
    private static class TableWithTenantTableAnnotationAndEmptyTenantColumn {}

    @TenantTable(tenantIdColumn = "ten_col_id")
    private static class TableWithTenantTableAnnotationAndSpecifiedTenantColumn {}
}
