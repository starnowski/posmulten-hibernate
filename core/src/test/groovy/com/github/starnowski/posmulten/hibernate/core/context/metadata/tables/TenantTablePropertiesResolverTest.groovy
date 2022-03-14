package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables

import com.github.starnowski.posmulten.hibernate.core.TenantTable
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
    def "should return null if type does not have correct annotation" ()
    {
        given:
            def persistentClass = Mock(PersistentClass)
            persistentClass.getMappedClass() >> TableWithoutTenantTableAnnotation.class

        when:
            def result = tested.resolve(persistentClass, table)

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
            def result = tested.resolve(persistentClass, prepareTable("user", ["id": "numeric"]))

        then:
            result.getTenantColumnName() == expectedTenantColumn

        where:
            clazz                                                   ||  expectedTenantColumn
            TableWithDefaultTenantTableAnnotation                   ||  null
            TableWithTenantTableAnnotationAndEmptyTenantColumn      ||  null
            TableWithTenantTableAnnotationAndSpecifiedTenantColumn  ||  "ten_col_id"
    }

    @Unroll
    def "should return object with table: #expectedTable, expected columns for primary keys: #expectedColumns" ()
    {
        given:
            def persistentClass = Mock(PersistentClass)
            persistentClass.getMappedClass() >> TableWithDefaultTenantTableAnnotation.class

        when:
            def result = tested.resolve(persistentClass, table)

        then:
            result.getTable() == expectedTable
            result.getPrimaryKeysColumnAndTypeMap() == expectedColumns

        where:
            table                                                                   ||  expectedTable   |  expectedColumns
            prepareTable("user", ["id": "numeric"])                                 ||  "user"    |   ["id": "numeric"]
            prepareTable("comments", ["comment_id": "long"])                        ||  "comments"  |   ["comment_id": "long"]
            prepareTable("posts", ["user_id": "numeric", "post_uuid" : "UUID"])     ||  "posts"   |   ["user_id": "numeric", "post_uuid" : "UUID"]
    }

    private static Table prepareTable(String name, Map<String, String> primaryColumns)
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
        when(primaryKey.getColumnIterator()).thenReturn(columns.iterator())
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
