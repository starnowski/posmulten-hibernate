package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables

import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import org.hibernate.mapping.ForeignKey
import spock.lang.Specification
import spock.lang.Unroll

class ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelperTest extends Specification {

    //TODO
    @Unroll
    def "should enrich builder with foreign key for table #table and columns #expectedColumnsReference, reference table #referenceTable, expected constraint name #cn, table columns #tableColumns, reference table columns #referenceTableColumns"() {
        given:
            def tested = new ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper()
            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def foreignKey = Mock(ForeignKey)
            def nameGenerator = Mock(NameGenerator)

        when:
            tested.enrichBuilder(builder, foreignKey, nameGenerator)

        then:
            1 * builder.createSameTenantConstraintForForeignKey(table, referenceTable, expectedColumnsReference, cn)

        where:
            table   |   referenceTable  |   cn  |   tableColumns    |   referenceTableColumns   ||  expectedColumnsReference
            "posts" |   "users"         |   "some_con"  |   "user_id"   |   "id"    ||  [user_id: "id"]
    }
}
