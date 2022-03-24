package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables

import org.hibernate.boot.Metadata
import org.hibernate.mapping.Table
import spock.lang.Specification

class CollectionResolverTest extends Specification {

    def tested = new CollectionResolver()

    def "should resolve collection for table when one of collection contains table"(){
        given:
            def metadata = Mock(Metadata)
            def table = prepareTable("tabExpected")
            table.equals(table) >> true
            def expectedCollection = mapCollectionForTable(table)
            Collection<org.hibernate.mapping.Collection> collection = [mapCollectionForTable(prepareTable("tab1")), expectedCollection, mapCollectionForTable(prepareTable("tab3"))]
            metadata.getCollectionBindings() >> collection

        when:
            def result = tested.resolve(metadata, table)

        then:
            result.is(expectedCollection)
    }

    def "should resolve null collection for table when none of collections contains table"(){
        given:
            def metadata = Mock(Metadata)
            def table = prepareTable("tabExpected")
            table.equals(table) >> true
            Collection<org.hibernate.mapping.Collection> collection = [mapCollectionForTable(prepareTable("tab1")), mapCollectionForTable(prepareTable("tab2")), mapCollectionForTable(prepareTable("tab3"))]
            metadata.getCollectionBindings() >> collection

        when:
            def result = tested.resolve(metadata, table)

        then:
            result == null
    }

    private Table prepareTable(String tableName)
    {
        def table = Mock(Table)
        table.getName() >> tableName
        return table
    }

    private org.hibernate.mapping.Collection mapCollectionForTable(Table table)
    {
        def col = Mock(org.hibernate.mapping.Collection)
        col.getCollectionTable() >> table
        return col
    }
}
