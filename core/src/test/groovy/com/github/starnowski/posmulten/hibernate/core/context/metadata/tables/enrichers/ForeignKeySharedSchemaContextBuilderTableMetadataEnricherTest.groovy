package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers

import com.github.starnowski.posmulten.hibernate.core.TenantTable
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder
import org.hibernate.boot.Metadata
import org.hibernate.mapping.ForeignKey
import org.hibernate.mapping.Table
import org.hibernate.service.spi.ServiceRegistryImplementor
import spock.lang.Specification

class ForeignKeySharedSchemaContextBuilderTableMetadataEnricherTest extends Specification {

    def tested = new ForeignKeySharedSchemaContextBuilderTableMetadataEnricher()

    def "should enrich builder for foreign keys that has reference to tenant table"()
    {
        given:
            def foreignKeyWithTenantTable1 = prepareForeignKey(TableWithTableAnnotation.class.getName())
            def foreignKeyWithTenantTable2 = prepareForeignKey(TableWithTableAnnotation.class.getName())
            def foreignKeyWithoutTenantTable1 = prepareForeignKey(TableWithoutTenantTableAnnotation.class.getName())
            def foreignKeyWithNullTable = prepareForeignKey(null)

            def serviceRegistryImplementor = Mock(ServiceRegistryImplementor)
            def posmultenUtilContext = Mock(PosmultenUtilContext)
            def helper = Mock(ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper)
            def nameGenerator = Mock(NameGenerator)
            serviceRegistryImplementor.getService(PosmultenUtilContext) >> posmultenUtilContext
            posmultenUtilContext.getNameGenerator() >> nameGenerator
            posmultenUtilContext.getForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper() >> helper

            def builder = Mock(DefaultSharedSchemaContextBuilder)
            def metadata = Mock(Metadata)
            def table = Mock(Table)
            table.getForeignKeyIterator() >> { [foreignKeyWithTenantTable1, foreignKeyWithNullTable, foreignKeyWithTenantTable2, foreignKeyWithoutTenantTable1].iterator() }

            tested.init(null, serviceRegistryImplementor)

        when:
            tested.enrich(builder, metadata, table)

        then:
            1 * helper.enrichBuilder(builder, foreignKeyWithTenantTable1, nameGenerator)
            1 * helper.enrichBuilder(builder, foreignKeyWithTenantTable2, nameGenerator)
            0 * helper._
    }

    private prepareForeignKey(String clazz)
    {
        def foreignKey = Mock(ForeignKey)
        foreignKey.getReferencedEntityName() >> clazz
        return foreignKey
    }

    private static class TableWithoutTenantTableAnnotation {}

    @TenantTable
    private static class TableWithTableAnnotation {}
}
