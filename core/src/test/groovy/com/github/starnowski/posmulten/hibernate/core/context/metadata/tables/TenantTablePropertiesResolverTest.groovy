package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables

import org.hibernate.mapping.Column
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.PrimaryKey
import org.hibernate.mapping.Table
import org.mockito.Mockito
import spock.lang.Specification

class TenantTablePropertiesResolverTest extends Specification {

    def tested = new TenantTablePropertiesResolver()

    def "should return null if type does not have correct annotation" ()
    {
        given:
            def persistentClass = Mock(PersistentClass)
            persistentClass.getMappedClass() >> TableWithoutTenantTableAnnotation.class

        when:
            def result = tested.resolve(persistentClass, )

        where:

    }

    private static Table prepareTable(String name, Map<String> primaryColumns)
    {
        def table = Mockito.mock(PrimaryKey.class)
        def primaryTable = Mockito.mock(Table.class)
        if (primaryColumns != null) {
            List<Column> columns = new ArrayList<>()
            primaryColumns.forEach({pc ->
                def column = Mockito.mock(Column.class)

            })
        }
        return table;
    }

    private static class TableWithoutTenantTableAnnotation {}
}
