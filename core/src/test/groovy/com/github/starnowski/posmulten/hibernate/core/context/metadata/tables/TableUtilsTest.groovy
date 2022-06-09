package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables

import org.hibernate.boot.Metadata
import org.hibernate.mapping.Collection
import org.hibernate.mapping.Column
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Table
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.stream.Collectors.toList

class TableUtilsTest extends Specification {

    def tested = new TableUtils()

    @Unroll
    def "should return true when table #table contains column #expectedColumn , all columns #columns"(){
        given:
            Table t = Mock(Table)
            def c = mapColumns(columns)
            t.getColumnIterator() >> { c.iterator() }

        when:
            def result = tested.hasColumnWithName(t, expectedColumn)

        then:
            result

        where:
            table   |   columns                         |   expectedColumn
            "user"  |   ["user_id", "uuid", "tenant"]   |   "tenant"
            "post"  |   ["user_id", "date", "id"]       |   "date"
    }

    @Unroll
    def "should return false when table #table do not contains column #expectedColumn , all columns #columns"(){
        given:
            Table t = Mock(Table)
            def c = mapColumns(columns)
            t.getColumnIterator() >> { c.iterator() }

        when:
            def result = tested.hasColumnWithName(t, expectedColumn)

        then:
            !result

        where:
            table   |   columns                         |   expectedColumn
            "user"  |   ["user_id", "uuid", "tenant"]   |   "some_id"
            "post"  |   ["user_id", "date", "id"]       |   "uuid"
    }

    @Unroll
    def "should return true when collection root is tenant table"(){
        given:
            Collection collection = Mock(Collection)
            PersistentClass owner = Mock(PersistentClass)
            Table table  = Mock(Table)
            Metadata metadata  = Mock(Metadata)
            TenantTablePropertiesResolver tablePropertiesResolver = Mock(TenantTablePropertiesResolver)
            collection.getOwner() >> owner
            owner.getMappedClass() >> Class1
            tablePropertiesResolver.resolve(Class1, table, metadata) >> new TenantTableProperties()

        when:
            def result = tested.isAnyCollectionComponentIsTenantTable(collection, tablePropertiesResolver, table, metadata)

        then:
            result
    }

    private List<Column> mapColumns(List<String> columnsNames){
        return columnsNames.stream().map({
            Column column = Mock(Column)
            column.getName() >> it
            return column
        }).collect(toList())
    }

    private static class Class1 {}

    private static class Class2 {}
}
