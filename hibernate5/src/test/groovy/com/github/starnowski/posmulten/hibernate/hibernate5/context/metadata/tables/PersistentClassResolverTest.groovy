package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables

import org.hibernate.boot.Metadata
import org.hibernate.mapping.PersistentClass
import org.hibernate.mapping.Table
import spock.lang.Specification
import spock.lang.Unroll

class PersistentClassResolverTest extends Specification {

    def tested = new PersistentClassResolver()

    def "should resolve PersistentClass for table when one of PersistentClass contains table"(){
        given:
            def metadata = Mock(Metadata)
            def table = prepareTable("tabExpected")
            table.equals(table) >> true
            table.isPhysicalTable() >> true
            def expectedCollection = mapPersistentClassForTable(table)
            Collection<PersistentClass> bindings = [mapPersistentClassForTable(prepareTable("tab1")), expectedCollection, mapPersistentClassForTable(prepareTable("tab3"))]
            metadata.getEntityBindings() >> bindings

        when:
            def result = tested.resolve(metadata, table)

        then:
            result.is(expectedCollection)
    }

    @Unroll
    def "should resolve null collection for table when none of collections contains table. table is physical: #isPhysicalTable"(){
        given:
            def metadata = Mock(Metadata)
            def table = prepareTable("tabExpected")
            table.equals(table) >> true
            table.isPhysicalTable() >> isPhysicalTable
            Collection<PersistentClass> bindings = [mapPersistentClassForTable(prepareTable("tab1")), mapPersistentClassForTable(prepareTable("tab2")), mapPersistentClassForTable(prepareTable("tab3"))]
            metadata.getEntityBindings() >> bindings

        when:
            def result = tested.resolve(metadata, table)

        then:
            result == null

        where:
            isPhysicalTable << [true, false]
    }

    private Table prepareTable(String tableName)
    {
        def table = Mock(Table)
        table.getName() >> tableName
        return table
    }

    private PersistentClass mapPersistentClassForTable(Table table)
    {
        def col = Mock(PersistentClass)
        col.getTable() >> table
        return col
    }
}
