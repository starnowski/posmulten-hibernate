package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables

import com.github.starnowski.posmulten.hibernate.core.TenantTable
import org.hibernate.mapping.Column
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.PrimaryKey
import org.hibernate.mapping.Table
import org.mockito.Mockito
import spock.lang.Specification
import spock.lang.Unroll

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

    private static Table prepareTable(String name, Map<String, String> primaryColumns)
    {
        def table = Mockito.mock(Table.class)
        def primaryKey = Mockito.mock(PrimaryKey.class)
        List<Column> columns = new ArrayList<>()
        if (primaryColumns != null) {
            primaryColumns.forEach({key, value ->
                def column = Mockito.mock(Column.class)
                Mockito.when(column.getName()).thenReturn(key)
                Mockito.when(column.getSqlType()).thenReturn(value)
                columns.add(column)
            })
        }
        Mockito.when(table.getPrimaryKey()).thenReturn(primaryKey)
        Mockito.when(primaryKey.getColumnIterator()).thenReturn(columns.iterator())
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
